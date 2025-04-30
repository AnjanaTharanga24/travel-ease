package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Indexed;

@Data
@Document(collection = "travel_packages")
public class TravelPackage {

    @Id
    private String id;
    private String destination;
    private String hotelName;
    private String packageName;
    private String description;
    private Double price;

}
