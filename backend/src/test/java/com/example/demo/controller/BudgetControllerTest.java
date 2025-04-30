package com.example.demo.controller;

import com.example.demo.dto.request.BudgetRequest;
import com.example.demo.dto.response.BudgetResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.BudgetCalculationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BudgetController.class)
class BudgetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetCalculationService budgetCalculationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void calculateBudget_WithValidRequest_ShouldReturnBudgetResponse() throws Exception {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Grand Hotel", "Paris Deluxe", 5, 2);

        BudgetResponse response = BudgetResponse.builder()
                .city("Paris")
                .hotelName("Grand Hotel")
                .packageName("Paris Deluxe")
                .numberOfDays(5)
                .numberOfTravelers(2)
                .totalBudget(5000.0)
                .build();

        when(budgetCalculationService.calculateBudget(any(BudgetRequest.class))).thenReturn(response);

        mockMvc.perform(post("/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Paris"))
                .andExpect(jsonPath("$.totalBudget").value(5000.0));
    }

    @Test
    void calculateBudget_WithInvalidHotel_ShouldReturnNotFound() throws Exception {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Unknown Hotel", "Paris Deluxe", 5, 2);

        when(budgetCalculationService.calculateBudget(any(BudgetRequest.class)))
                .thenThrow(new NotFoundException("Hotel not found"));

        mockMvc.perform(post("/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}