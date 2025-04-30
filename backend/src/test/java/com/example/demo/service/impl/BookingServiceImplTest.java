package com.example.demo.service.impl;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Test
    void bookTravel_WithValidRequest_ShouldReturnBookingResponse() throws NotFoundException , CustomException {
        BookingRequest request = new BookingRequest(
                null, "user1", "Paris", "2023-12-25", 2, "Grand Hotel");

        User mockUser = new User();
        Hotel mockHotel = new Hotel();
        mockHotel.setCity("Paris");
        mockHotel.setHotelNames(List.of("Grand Hotel"));

        when(userRepository.findById("user1")).thenReturn(Optional.of(mockUser));
        when(hotelRepository.findByCity("Paris")).thenReturn(mockHotel);
        when(hotelRepository.findByHotelNames("Grand Hotel")).thenReturn(mockHotel);
        when(bookingRepository.save(any(Booking.class))).thenAnswer(invocation -> {
            Booking booking = invocation.getArgument(0);
            booking.setId("booking1");
            return booking;
        });

        BookingResponse response = bookingService.bookTravel(request);

        assertNotNull(response);
        assertEquals("booking1", response.getId());
        assertEquals("user1", response.getUserId());
        assertEquals("Paris", response.getDestination());
        assertEquals("2023-12-25", response.getDate());
        assertEquals(2, response.getNumberOfTravelers());
        assertEquals("Grand Hotel", response.getHotelSelection());

        verify(userRepository, times(1)).findById("user1");
        verify(hotelRepository, times(1)).findByCity("Paris");
        verify(hotelRepository, times(1)).findByHotelNames("Grand Hotel");
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void bookTravel_WithInvalidUserId_ShouldThrowNotFoundException() {
        BookingRequest request = new BookingRequest(
                null, "nonexistentUser", "Paris", "2023-12-25", 2, "Grand Hotel");

        when(userRepository.findById("nonexistentUser")).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookingService.bookTravel(request));

        assertEquals("user not found with id : nonexistentUser", exception.getMessage());
        verify(userRepository, times(1)).findById("nonexistentUser");
        verifyNoInteractions(hotelRepository, bookingRepository);
    }

    @Test
    void bookTravel_WithInvalidDestination_ShouldThrowNotFoundException() {
        BookingRequest request = new BookingRequest(
                null, "user1", "NonexistentCity", "2023-12-25", 2, "Grand Hotel");

        when(userRepository.findById("user1")).thenReturn(Optional.of(new User()));
        when(hotelRepository.findByCity("NonexistentCity")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookingService.bookTravel(request));

        assertEquals("NonexistentCity is not in our city data", exception.getMessage());
        verify(userRepository, times(1)).findById("user1");
        verify(hotelRepository, times(1)).findByCity("NonexistentCity");
        verifyNoMoreInteractions(hotelRepository);
        verifyNoInteractions(bookingRepository);
    }

    @Test
    void bookTravel_WithInvalidHotel_ShouldThrowNotFoundException() {
        BookingRequest request = new BookingRequest(
                null, "user1", "Paris", "2023-12-25", 2, "Nonexistent Hotel");

        User mockUser = new User();
        Hotel mockHotel = new Hotel();
        mockHotel.setCity("Paris");
        mockHotel.setHotelNames(List.of("Grand Hotel"));

        when(userRepository.findById("user1")).thenReturn(Optional.of(mockUser));
        when(hotelRepository.findByCity("Paris")).thenReturn(mockHotel);
        when(hotelRepository.findByHotelNames("Nonexistent Hotel")).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookingService.bookTravel(request));

        assertEquals("there is no hotel named : Nonexistent Hotel", exception.getMessage());
        verify(userRepository, times(1)).findById("user1");
        verify(hotelRepository, times(1)).findByCity("Paris");
        verify(hotelRepository, times(1)).findByHotelNames("Nonexistent Hotel");
        verifyNoInteractions(bookingRepository);
    }

    @Test
    void bookTravel_WithZeroTravelers_ShouldThrowCustomException() {
        BookingRequest request = new BookingRequest(
                null, "user1", "Paris", "2023-12-25", 0, "Grand Hotel");

        when(userRepository.findById("user1")).thenReturn(Optional.of(new User()));

        CustomException exception = assertThrows(CustomException.class,
                () -> bookingService.bookTravel(request));

        assertEquals("Number of travelers must be positive", exception.getMessage());
        verify(userRepository, times(1)).findById("user1");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(hotelRepository, bookingRepository);
    }

    @Test
    void bookTravel_WithNegativeTravelers_ShouldThrowCustomException() {
        BookingRequest request = new BookingRequest(
                null, "user1", "Paris", "2023-12-25", -1, "Grand Hotel");

        when(userRepository.findById("user1")).thenReturn(Optional.of(new User()));

        CustomException exception = assertThrows(CustomException.class,
                () -> bookingService.bookTravel(request));

        assertEquals("Number of travelers must be positive", exception.getMessage());
        verify(userRepository, times(1)).findById("user1");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(hotelRepository, bookingRepository);
    }

    @Test
    void bookTravel_WithNullDate_ShouldThrowCustomException() {
        BookingRequest request = new BookingRequest(
                null, "user1", "Paris", null, 2, "Grand Hotel");

        when(userRepository.findById("user1")).thenReturn(Optional.of(new User()));

        CustomException exception = assertThrows(CustomException.class,
                () -> bookingService.bookTravel(request));

        assertEquals("Date cannot be null", exception.getMessage());
        verify(userRepository, times(1)).findById("user1");
        verifyNoMoreInteractions(userRepository);
        verifyNoInteractions(hotelRepository, bookingRepository);
    }
}