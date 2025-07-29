package com.example.airbnb.data.repository;

import com.example.airbnb.data.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByGuestId(Long guestId);

    boolean existsByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long propertyId, LocalDate end, LocalDate start);

}
