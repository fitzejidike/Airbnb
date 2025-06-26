package com.example.airbnb.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class PropertyAvailability {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Property property;

    private LocalDate availableFrom;
    private LocalDate availableTo;
}

