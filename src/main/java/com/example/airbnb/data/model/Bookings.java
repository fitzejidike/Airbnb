package com.example.airbnb.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Bookings {
    @Id
    private Long id;
    private Long propertyId;



}
