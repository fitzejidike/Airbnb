package com.example.airbnb.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Bookings extends JpaRepository<Bookings,Long> {
}
