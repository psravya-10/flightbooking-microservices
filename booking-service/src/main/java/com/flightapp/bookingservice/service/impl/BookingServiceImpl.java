package com.flightapp.bookingservice.service.impl;

import com.flightapp.bookingservice.config.RabbitConfig;
import com.flightapp.bookingservice.dto.BookingResult;
import com.flightapp.bookingservice.feign.FlightClient;
import com.flightapp.bookingservice.model.Booking;
import com.flightapp.bookingservice.model.Passenger;
import com.flightapp.bookingservice.repository.BookingRepository;
import com.flightapp.bookingservice.repository.PassengerRepository;
import com.flightapp.bookingservice.service.BookingService;
import com.flightapp.common.dto.BookingRequest;
import com.flightapp.common.enums.BookingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final PassengerRepository passengerRepo;
    private final FlightClient flightClient;
    private final RabbitTemplate rabbit;

    @Override
    @CircuitBreaker(name = "flightServiceCB", fallbackMethod = "flightServiceFallback")
    public BookingResult book(Long flightId, BookingRequest request) {

        var flight = flightClient.getFlight(flightId);

        flightClient.reserveSeats(flightId, Map.of("seats", request.passengers().size()));

        Booking b = new Booking();
        b.setEmail(request.email());
        b.setFlightId(flightId);
        b.setPassengerCount(request.passengers().size());
        b.setBookingStatus(BookingStatus.CONFIRMED);
        b.setPnr("PNR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        b.setTotalPrice(flight.price.multiply(BigDecimal.valueOf(request.passengers().size())));

        Booking saved = bookingRepo.save(b);

        List<Passenger> ps = request.passengers()
                .stream()
                .map(p -> new Passenger(null, saved.getId(), p.name(), p.gender(), p.age(), p.seatNumber(), p.meal()))
                .collect(Collectors.toList());

        passengerRepo.saveAll(ps);

        rabbit.convertAndSend(
                RabbitConfig.EXCHANGE,
                "booking.created",
                Map.of("email", saved.getEmail(), "pnr", saved.getPnr(), "type", "BOOKED")
        );

        return new BookingResult(saved.getId(), saved.getPnr());
    }

    @Override
    public Booking getByPnr(String pnr) {
        return bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("PNR not found"));
    }

    @Override
    public List<Booking> history(String email) {
        return bookingRepo.findByEmail(email);
    }

    @Override
    public void cancel(String pnr) {
        Booking b = bookingRepo.findByPnr(pnr)
                .orElseThrow(() -> new RuntimeException("PNR not found"));

        b.setBookingStatus(BookingStatus.CANCELLED);
        bookingRepo.save(b);

        rabbit.convertAndSend(
                RabbitConfig.EXCHANGE,
                "booking.cancelled",
                Map.of("email", b.getEmail(), "pnr", b.getPnr(), "type", "CANCELLED")
        );
    }
    
    public BookingResult flightServiceFallback(Long flightId, BookingRequest request, Throwable ex) {
        throw new RuntimeException("Flight service unavailable. Please try again later.");
    }
}

