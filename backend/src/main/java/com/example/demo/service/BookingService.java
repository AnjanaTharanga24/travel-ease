package com.example.demo.service;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.exception.NotFoundException;

public interface BookingService {

    BookingResponse bookTravel(BookingRequest bookingRequest)throws NotFoundException;
}
