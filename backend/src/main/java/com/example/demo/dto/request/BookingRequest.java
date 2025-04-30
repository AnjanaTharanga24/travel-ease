package com.example.demo.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {

    private String id;

    @NotBlank(message = "User ID is required")
    private String userId;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotBlank(message = "Date is required")
    private String date;

    @NotNull(message = "Number of travelers is required")
    @Positive(message = "Number of travelers must be positive")
    private Integer numberOfTravelers;

    @NotBlank(message = "Hotel selection is required")
    private String hotelSelection;
}