package com.flightapp.bookingservice.service;

import com.flightapp.bookingservice.dto.BookingResult;
import com.flightapp.bookingservice.model.Booking;
import com.flightapp.common.dto.BookingRequest;

import java.util.List;

public interface BookingService {

    BookingResult book(Long flightId, BookingRequest request);

    Booking getByPnr(String pnr);

    List<Booking> history(String email);

    void cancel(String pnr);
}
