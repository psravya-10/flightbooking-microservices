package com.flightapp.flightservice.repository;

import com.flightapp.flightservice.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByFromPlaceAndToPlace(String fromPlace, String toPlace);
}
