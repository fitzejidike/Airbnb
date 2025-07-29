package com.example.airbnb.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class BookingRequestDTO {
    private Long propertyId;
    private Long guestId;
    private LocalDate startDate;
    private LocalDate endDate;

}
