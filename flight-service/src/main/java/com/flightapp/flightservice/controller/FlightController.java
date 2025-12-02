package com.flightapp.flightservice.controller;

import com.flightapp.common.dto.ApiResponse;
import com.flightapp.common.dto.FlightDto;
import com.flightapp.flightservice.model.Flight;
import com.flightapp.flightservice.service.FlightService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/flight")
public class FlightController {

    private final FlightService service;

    public FlightController(FlightService service) {
        this.service = service;
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<ApiResponse> add(@RequestBody Flight flight) {
        var saved = service.addFlight(flight);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(201, "Flight added", Map.of("id", saved.getId())));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestBody Map<String, String> req) {
        return ResponseEntity.ok(
                new ApiResponse(200, "Search results",
                        service.search(req.get("fromPlace"), req.get("toPlace")))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getFlight(@PathVariable Long id) {
        FlightDto dto = service.getFlight(id);
        return ResponseEntity.ok(new ApiResponse(200, "Flight found", dto));
    }

    @PostMapping("/reserve-seats/{id}")
    public ResponseEntity<ApiResponse> reserve(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> body) {

        service.reserveSeats(id, body.get("seats"));
        return ResponseEntity.ok(
                new ApiResponse(200, "Seats reserved", Map.of("flightId", id))
        );
    }
}
