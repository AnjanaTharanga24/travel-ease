package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BudgetResponse {

    private String city;
    private String hotelName;
    private String packageName;
    private Integer numberOfDays;
    private Integer numberOfTravelers;
    private Double totalBudget;
}
