package com.flightapp.bookingservice.feign;

import com.flightapp.common.dto.FlightDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@FeignClient(name = "flight-service", path = "/internal/flight")
public interface FlightClient {

    @GetMapping("/{id}")
    FlightDto getFlight(@PathVariable("id") Long id);

    @PostMapping("/reserve-seats/{id}")
    Map<String, Object> reserveSeats(@PathVariable("id") Long id,
                                     @RequestBody Map<String, Integer> body);
}
