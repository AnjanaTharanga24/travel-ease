package com.example.demo.service.impl;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
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

import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private HotelRepository hotelRepository;

    @Override
    public BookingResponse bookTravel(BookingRequest bookingRequest) throws NotFoundException {

        Optional<User> userOptional = userRepository.findById(bookingRequest.getUserId());

        if (!userOptional.isPresent()){
            throw new NotFoundException("user not found with id : " + bookingRequest.getUserId());
        }

        Booking booking = new Booking();
        booking.setUserId(bookingRequest.getUserId());
        booking.setDestination(bookingRequest.getDestination());
        booking.setDate(bookingRequest.getDate());
        booking.setNumberOfTravelers(bookingRequest.getNumberOfTravelers());
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
            throw new NotFoundException("there are not hotels for " +city);
        }
        return foundHotels;
    }
}
