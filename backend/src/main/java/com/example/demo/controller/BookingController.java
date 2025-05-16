package com.example.demo.controller;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Hotel;
import com.example.demo.service.BookingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class BookingController {

    private BookingService bookingService;

    @PostMapping("/book")
    public BookingResponse bookTravel(@RequestBody BookingRequest bookingRequest) throws NotFoundException , CustomException {
        return bookingService.bookTravel(bookingRequest);
    }

    @PostMapping("/hotels")
    public Hotel createHotel(@RequestBody Hotel hotel){
        return bookingService.createHotel(hotel);
    }

    @GetMapping("/hotels/{city}")
    public Hotel getHotelsByCity(@PathVariable("city") String city) throws NotFoundException{
        return bookingService.findHotelByCity(city);
    }
}
