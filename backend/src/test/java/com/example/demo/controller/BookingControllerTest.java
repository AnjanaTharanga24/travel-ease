package com.example.demo.controller;

import com.example.demo.dto.request.BookingRequest;
import com.example.demo.dto.response.BookingResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void bookTravel_ShouldReturnCreatedBooking() throws Exception {
        BookingRequest request = new BookingRequest(
                null, "user1", "Paris", "2023-12-25", 2, "Grand Hotel");

        BookingResponse response = BookingResponse.builder()
                .id("booking1")
                .userId("user1")
                .destination("Paris")
                .date("2023-12-25")
                .numberOfTravelers(2)
                .hotelSelection("Grand Hotel")
                .build();

        when(bookingService.bookTravel(any(BookingRequest.class))).thenReturn(response);

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("booking1"))
                .andExpect(jsonPath("$.destination").value("Paris"));
    }

    @Test
    void bookTravel_WithInvalidRequest_ShouldReturnBadRequest() throws Exception {
        BookingRequest invalidRequest = new BookingRequest(
                null, "", "", "", -1, "");

        mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getHotelsByCity_ShouldReturnHotels() throws Exception {
        mockMvc.perform(get("/hotels/Paris"))
                .andExpect(status().isOk());
    }

    @Test
    void getHotelsByCity_WhenNotFound_ShouldReturnNotFound() throws Exception {
        when(bookingService.findHotelByCity(anyString()))
                .thenThrow(new NotFoundException("No hotels found"));

        mockMvc.perform(get("/hotels/UnknownCity"))
                .andExpect(status().isNotFound());
    }
}