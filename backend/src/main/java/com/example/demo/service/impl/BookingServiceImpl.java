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
import com.example.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private HotelRepository hotelRepository;

    @Override
    public BookingResponse bookTravel(BookingRequest bookingRequest) throws NotFoundException, CustomException {
        // Validate user exists first
        Optional<User> userOptional = userRepository.findById(bookingRequest.getUserId());
        if (!userOptional.isPresent()){
            throw new NotFoundException("user not found with id : " + bookingRequest.getUserId());
        }

        // Validate date is not null
        if (bookingRequest.getDate() == null) {
            throw new CustomException("Date cannot be null");
        }

        // Validate number of travelers is positive
        if (bookingRequest.getNumberOfTravelers() <= 0) {
            throw new CustomException("Number of travelers must be positive");
        }

        // Proceed with booking logic
        Booking booking = new Booking();
        booking.setUserId(bookingRequest.getUserId());

        Hotel optionalHotel = hotelRepository.findByCity(bookingRequest.getDestination());
        if (optionalHotel == null){
            throw new NotFoundException(bookingRequest.getDestination() + " is not in our city data");
        }

        booking.setDestination(bookingRequest.getDestination());
        booking.setDate(bookingRequest.getDate());
        booking.setNumberOfTravelers(bookingRequest.getNumberOfTravelers());

        Hotel foundHotel = hotelRepository.findByHotelNames(bookingRequest.getHotelSelection());
        if (foundHotel == null){
            throw new NotFoundException("there is no hotel named : " + bookingRequest.getHotelSelection());
        }

        booking.setHotelSelection(bookingRequest.getHotelSelection());

        Booking bookedTravel = bookingRepository.save(booking);

        return BookingResponse.builder()
                .id(bookedTravel.getId())
                .userId(bookedTravel.getUserId())
                .destination(bookedTravel.getDestination())
                .date(bookedTravel.getDate())
                .numberOfTravelers(bookedTravel.getNumberOfTravelers())
                .hotelSelection(bookedTravel.getHotelSelection())
                .build();
    }

    @Override
    public Hotel createHotel(Hotel hotel) {
        Hotel newHotel = new Hotel();
        newHotel.setCity(hotel.getCity());
        newHotel.setHotelNames(hotel.getHotelNames());

        return hotelRepository.save(newHotel);
    }

    @Override
    public Hotel findHotelByCity(String city) throws NotFoundException {
        Hotel foundHotels = hotelRepository.findByCity(city);

        if (foundHotels == null){
            throw new NotFoundException("there are not hotels for " + city);
        }
        return foundHotels;
    }
}