package com.example.airbnb.Controller;

import com.example.airbnb.dtos.request.BookingRequestDTO;
import com.example.airbnb.dtos.responses.ApiResponse;
import com.example.airbnb.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "Endpoints for managing bookings")
public class BookingController {

    private final BookingService bookingService;

    @Operation(  summary = "Create a new booking",
            description = "Creates a booking for a guest against a property"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createBooking(
            @RequestBody BookingRequestDTO dto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(bookingService.createBooking(dto), "Booking created successfully"));
    }

    @Operation(
            summary = "Get bookings by guest",
            description = "Fetches all bookings associated with a specific guest ID"
    )
   @GetMapping("/guest/{guestId}")
    public ResponseEntity<ApiResponse<?>> getBookingsByGuest(
            @Parameter(description = "ID of the guest whose bookings to fetch")
            @PathVariable Long guestId
    ) {
        return ResponseEntity.ok(ApiResponse.success(
                bookingService.getBookingsByGuest(guestId), "Bookings fetched"));
    }

    @Operation(
            summary = "Cancel a booking",
            description = "Cancels an existing booking by its ID"
    )
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<?>> cancel(
            @Parameter(description = "ID of the booking to cancel")
            @PathVariable Long bookingId
    ) {
        bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(ApiResponse.success(null, "Booking cancelled"));
    }
}
