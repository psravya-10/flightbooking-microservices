package com.flightapp.flightservice.service.impl;

import com.flightapp.common.dto.FlightDto;
import com.flightapp.flightservice.model.Flight;
import com.flightapp.flightservice.repository.FlightRepository;
import com.flightapp.flightservice.service.FlightService;
import com.flightapp.flightservice.exception.FlightNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    private final FlightRepository repo;

    public FlightServiceImpl(FlightRepository repo) {
        this.repo = repo;
    }

    @Override
    public Flight addFlight(Flight flight) {
        flight.setAvailableSeats(flight.getTotalSeats());
        return repo.save(flight);
    }

    @Override
    public List<FlightDto> search(String from, String to) {
        return repo.findByFromPlaceAndToPlace(from, to)
                .stream()
                .map(f -> new FlightDto(
                        f.getId(), f.getAirlineName(), f.getFlightNumber(),
                        f.getFromPlace(), f.getToPlace(),
                        f.getDepartureTime(), f.getArrivalTime(),
                        f.getTotalSeats(), f.getAvailableSeats(), f.getPrice()
                ))
                .toList();
    }

    @Override
    public FlightDto getFlight(Long id) {
        Flight f = repo.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
        return new FlightDto(
                f.getId(), f.getAirlineName(), f.getFlightNumber(),
                f.getFromPlace(), f.getToPlace(),
                f.getDepartureTime(), f.getArrivalTime(),
                f.getTotalSeats(), f.getAvailableSeats(), f.getPrice()
        );
    }

    @Override
    public void reserveSeats(Long id, int seats) {
        Flight f = repo.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));

        if (f.getAvailableSeats() < seats)
            throw new RuntimeException("Not enough seats");

        f.setAvailableSeats(f.getAvailableSeats() - seats);
        repo.save(f);
    }
}
