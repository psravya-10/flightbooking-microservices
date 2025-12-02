package com.flightapp.bookingservice.controller;

import com.flightapp.bookingservice.dto.BookingResult;
import com.flightapp.bookingservice.service.BookingService;
import com.flightapp.common.dto.ApiResponse;
import com.flightapp.common.dto.BookingRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/internal/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService service;

    @PostMapping("/book/{flightId}")
    public ResponseEntity<ApiResponse> book(
            @PathVariable Long flightId,
            @Valid @RequestBody BookingRequest req
    ) {
        BookingResult result = service.book(flightId, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(201, "Booking successful", result));
    }

    @GetMapping("/ticket/{pnr}")
    public ResponseEntity<ApiResponse> ticket(@PathVariable String pnr) {
        return ResponseEntity.ok(
                new ApiResponse(200, "Ticket found", service.getByPnr(pnr))
        );
    }

    @GetMapping("/history/{email}")
    public ResponseEntity<ApiResponse> history(@PathVariable String email) {
        return ResponseEntity.ok(
                new ApiResponse(200, "History fetched", service.history(email))
        );
    }

    @DeleteMapping("/cancel/{pnr}")
    public ResponseEntity<ApiResponse> cancel(@PathVariable String pnr) {
        service.cancel(pnr);
        return ResponseEntity.ok(
                new ApiResponse(200, "Booking cancelled", pnr)
        );
    }
}
