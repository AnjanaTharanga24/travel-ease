package com.example.demo.service;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Hotel;

public interface BookingService {

    BookingResponse bookTravel(BookingRequest bookingRequest)throws NotFoundException , CustomException;
    Hotel createHotel(Hotel hotel);
    Hotel findHotelByCity(String city) throws NotFoundException;
}
