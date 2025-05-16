package com.example.demo.service.impl;

import com.example.demo.dto.request.BudgetRequest;
import com.example.demo.dto.response.BudgetResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.TravelPackageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetCalculationServiceImplTest {

    @Mock
    private TravelPackageRepository travelPackageRepository;

    @InjectMocks
    private BudgetCalculationServiceImpl budgetCalculationService;

    @Test
    void calculateBudget_WithValidData_ShouldReturnCorrectBudget() throws NotFoundException {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Grand Hotel", "Paris Deluxe", 5, 2);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setId("1");
        travelPackage.setHotelName("Grand Hotel");
        travelPackage.setPackageName("Paris Deluxe");
        travelPackage.setPrice(500.0);

        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Grand Hotel"))
                .thenReturn(Collections.singletonList(travelPackage));
        when(travelPackageRepository.findByHotelNameAndPackageName("Grand Hotel", "Paris Deluxe"))
                .thenReturn(Optional.of(travelPackage));

        BudgetResponse response = budgetCalculationService.calculateBudget(request);

        assertNotNull(response);
        assertEquals(5000.0, response.getTotalBudget());
        assertEquals(5, response.getNumberOfDays());
        assertEquals(2, response.getNumberOfTravelers());
    }

    @Test
    void calculateBudget_WithInvalidHotel_ShouldThrowException() {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Unknown Hotel", "Paris Deluxe", 5, 2);

        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Unknown Hotel"))
                .thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> {
            budgetCalculationService.calculateBudget(request);
        });
    }

    @Test
    void calculateBudget_WithInvalidPackage_ShouldThrowException() {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Grand Hotel", "Unknown Package", 5, 2);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setHotelName("Grand Hotel");

        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Grand Hotel"))
                .thenReturn(Collections.singletonList(travelPackage));
        when(travelPackageRepository.findByHotelNameAndPackageName("Grand Hotel", "Unknown Package"))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            budgetCalculationService.calculateBudget(request);
        });
    }

    @Test
    void calculateBudget_WithZeroDays_ShouldThrowException() {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Grand Hotel", "Paris Deluxe", 0, 2);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setHotelName("Grand Hotel");
        travelPackage.setPackageName("Paris Deluxe");
        travelPackage.setPrice(500.0);

        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Grand Hotel"))
                .thenReturn(Collections.singletonList(travelPackage));
        when(travelPackageRepository.findByHotelNameAndPackageName("Grand Hotel", "Paris Deluxe"))
                .thenReturn(Optional.of(travelPackage));

        assertThrows(NotFoundException.class, () -> {
            budgetCalculationService.calculateBudget(request);
        });
    }

    @Test
    void calculateBudget_WithZeroTravelers_ShouldThrowException() {
        BudgetRequest request = new BudgetRequest(
                "1", "Paris", "Grand Hotel", "Paris Deluxe", 5, 0);

        TravelPackage travelPackage = new TravelPackage();
        travelPackage.setHotelName("Grand Hotel");
        travelPackage.setPackageName("Paris Deluxe");
        travelPackage.setPrice(500.0);

        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Grand Hotel"))
                .thenReturn(Collections.singletonList(travelPackage));
        when(travelPackageRepository.findByHotelNameAndPackageName("Grand Hotel", "Paris Deluxe"))
                .thenReturn(Optional.of(travelPackage));

        assertThrows(NotFoundException.class, () -> {
            budgetCalculationService.calculateBudget(request);
        });
    }
}
