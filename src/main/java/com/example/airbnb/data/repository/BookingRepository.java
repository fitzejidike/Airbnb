package com.example.airbnb.data.repository;

import com.example.airbnb.data.model.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Bookings,Long> {
}
