package com.flightapp.bookingservice.model;

import com.flightapp.common.enums.Gender;
import com.flightapp.common.enums.MealType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long bookingId;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private Integer age;

    private String seatNumber;

    @Enumerated(EnumType.STRING)
    private MealType mealType;
}
