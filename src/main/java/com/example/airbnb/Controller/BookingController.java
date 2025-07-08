package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.BookingRequestDTO;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createBooking(@RequestBody BookingRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookingService.createBooking(dto), "Booking created successfully"));
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<ApiResponse<?>> getBookingsByGuest(@PathVariable Long guestId) {
        return ResponseEntity.ok(ApiResponse.success(
                bookingService.getBookingsByGuest(guestId), "Bookings fetched"));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<?>> cancel(@PathVariable Long bookingId) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(ApiResponse.success(null, "Booking cancelled"));
    }
}
