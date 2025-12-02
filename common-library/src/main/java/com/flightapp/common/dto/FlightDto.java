package com.flightapp.common.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightDto {

    public Long id;
    public String airlineName;
    public String flightNumber;
    public String fromPlace;
    public String toPlace;
    public LocalDateTime departureTime;
    public LocalDateTime arrivalTime;
    public Integer totalSeats;
    public Integer availableSeats;
    public BigDecimal price;

    public FlightDto() {}

    public FlightDto(Long id, String airlineName, String flightNumber,
                     String fromPlace, String toPlace,
                     LocalDateTime departureTime, LocalDateTime arrivalTime,
                     Integer totalSeats, Integer availableSeats, BigDecimal price) {

        this.id = id;
        this.airlineName = airlineName;
        this.flightNumber = flightNumber;
        this.fromPlace = fromPlace;
        this.toPlace = toPlace;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
        this.price = price;
    }
}
