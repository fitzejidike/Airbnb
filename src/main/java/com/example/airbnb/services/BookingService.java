package com.example.airbnb.services;

import com.example.airbnb.dtos.request.BookingRequestDTO;
import com.example.airbnb.dtos.responses.BookingResponseDTO;

import java.util.List;

public interface BookingService {
    BookingResponseDTO createBooking(BookingRequestDTO dto);
    List<BookingResponseDTO> getBookingsByGuest(Long guestId);
    void cancelBooking(Long bookingId);
}
