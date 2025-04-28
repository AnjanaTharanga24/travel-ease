package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    private String id;
    private String userId;
    private String destination;
    private String date;
    private Integer numberOfTravelers;
    private String hotelSelection;

}
