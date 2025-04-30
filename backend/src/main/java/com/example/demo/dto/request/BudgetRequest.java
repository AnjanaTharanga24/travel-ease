package com.example.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BudgetRequest {

    private String id;
    private String city;
    private String hotelName;
    private String packageName;
    private Integer numberOfDays;
    private Integer numberOfTravelers;

}
