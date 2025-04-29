package com.example.demo.controller;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Hotel;
import com.example.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BookingController {

    private BookingService bookingService;

    @PostMapping("/book")
    public BookingResponse bookTravel(@RequestBody BookingRequest bookingRequest) throws NotFoundException {
        return bookingService.bookTravel(bookingRequest);
    }

    @PostMapping("/hotels")
    public Hotel createHotel(@RequestBody Hotel hotel){
        return bookingService.createHotel(hotel);
    }
}
