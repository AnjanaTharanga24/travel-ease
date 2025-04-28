package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private String id;
    private String userId;
    private String destination;
    private String date;
    private Integer numberOfTravelers;
    private String hotelSelection;

}
