package com.flightapp.flightservice.service;

import com.flightapp.common.dto.FlightDto;
import com.flightapp.flightservice.model.Flight;

import java.util.List;

public interface FlightService {
    Flight addFlight(Flight flight);
    List<FlightDto> search(String from, String to);
    FlightDto getFlight(Long id);
    void reserveSeats(Long id, int seats);
}
