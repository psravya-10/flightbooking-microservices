package com.flightapp.common.dto;

import com.flightapp.common.enums.Gender;
import com.flightapp.common.enums.MealType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PassengerDto(
        @NotBlank String name,
        @NotNull Gender gender,
        @Min(1) Integer age,
        @NotBlank String seatNumber,
        @NotNull MealType meal
) {}
