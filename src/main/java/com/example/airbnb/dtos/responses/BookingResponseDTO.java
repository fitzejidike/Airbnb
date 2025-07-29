package com.example.airbnb.dtos.responses;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private Long id;
    private String propertyLocation;
    private String guestUsername;
    private LocalDate startDate;
    private LocalDate endDate;
}
