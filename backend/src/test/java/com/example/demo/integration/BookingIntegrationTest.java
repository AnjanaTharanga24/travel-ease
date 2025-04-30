package com.example.demo.integration;


import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.model.Booking;
import com.example.demo.model.Hotel;
import com.example.demo.model.User;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.BookingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BookingIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @AfterEach
    void tearDown() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();
        hotelRepository.deleteAll();
    }

//    @Test
//    void bookTravel_WithValidData_ShouldSaveBooking() throws Exception {
//        // Setup
//        User user = new User();
//        user.setId("user1");
//        userRepository.save(user);
//
//        Hotel hotel = new Hotel();
//        hotel.setCity("Paris");
//        hotel.setHotelNames("Grand Hotel");
//        hotelRepository.save(hotel);
//
//        BookingRequest request = new BookingRequest(
//                null, "user1", "Paris", "2023-12-25", 2, "Grand Hotel");
//
//        // Test
//        BookingResponse response = bookingService.bookTravel(request);
//
//        // Verify
//        assertNotNull(response.getId());
//        assertEquals("Paris", response.getDestination());
//
//        Optional<Booking> savedBooking = bookingRepository.findById(response.getId());
//        assertTrue(savedBooking.isPresent());
//        assertEquals("user1", savedBooking.get().getUserId());
//    }
}
