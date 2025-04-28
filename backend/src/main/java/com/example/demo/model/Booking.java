package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "booking")
public class Booking {

    @Id
    private String id;
    private String userId;
    private String destination;
    private String date;
    private Integer numberOfTravelers;
    private String hotelSelection;

}
