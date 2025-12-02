package com.flightapp.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record BookingRequest(
        @Email @NotBlank String email,
        @NotEmpty List<PassengerDto> passengers
) {}
