package com.example.airbnb.services.implementation;

import com.example.airbnb.data.model.Booking;
import com.example.airbnb.data.model.Property;
import com.example.airbnb.data.model.User;
import com.example.airbnb.data.repository.BookingRepository;
import com.example.airbnb.data.repository.PropertyRepository;
import com.example.airbnb.dtos.request.BookingRequestDTO;
import com.example.airbnb.dtos.responses.BookingResponseDTO;
import com.example.airbnb.services.BookingService;
import com.example.airbnb.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PropertyRepository propertyRepository;
    private final UserService userService;

    @Override
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        if (bookingRepository.existsByPropertyIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                dto.getPropertyId(), dto.getEndDate(), dto.getStartDate())) {
            throw new RuntimeException("This property is already booked for those dates");
        }

        Property property = propertyRepository.findById(dto.getPropertyId())
                .orElseThrow(() -> new EntityNotFoundException("Property not found"));

        User guest = userService.getUserById(dto.getGuestId());

        Booking booking = Booking.builder()
                .property(property)
                .guest(guest)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .timeCreated(LocalDateTime.now())
                .build();

        bookingRepository.save(booking);

        return BookingResponseDTO.builder()
                .id(booking.getId())
                .propertyLocation(property.getLocation())
                .guestUsername(guest.getUsername())
                .startDate(booking.getStartDate())
                .endDate(booking.getEndDate())
                .build();
    }

    @Override
    public List<BookingResponseDTO> getBookingsByGuest(Long guestId) {
        return bookingRepository.findByGuestId(guestId).stream()
                .map(b -> BookingResponseDTO.builder()
                        .id(b.getId())
                        .propertyLocation(b.getProperty().getLocation())
                        .guestUsername(b.getGuest().getUsername())
                        .startDate(b.getStartDate())
                        .endDate(b.getEndDate())
                        .build())
                .toList();
    }

    @Override
    public void cancelBooking(Long bookingId) {
        bookingRepository.deleteById(bookingId);
    }
}



