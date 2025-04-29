package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document(collection = "hotel")
public class Hotel {

    @Id
    private String id;
    private String city;
    private List<String> hotelNames;
}
