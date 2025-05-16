package com.example.demo.service.impl;

import com.example.demo.dto.request.BudgetRequest;
import com.example.demo.dto.response.BudgetResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.TravelPackageRepository;
import com.example.demo.service.BudgetCalculationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BudgetCalculationServiceImpl implements BudgetCalculationService {

    private TravelPackageRepository travelPackageRepository;

    @Override
    public BudgetResponse calculateBudget(BudgetRequest budgetRequest) throws NotFoundException {
        List<TravelPackage> travelPackages = travelPackageRepository.findAllByHotelNameIgnoreCase(budgetRequest.getHotelName());

        if (travelPackages.isEmpty()) {
            throw new NotFoundException("That hotel name is not included in our system");
        }

        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findByHotelNameAndPackageName(
                budgetRequest.getHotelName(),
                budgetRequest.getPackageName());

        if (optionalTravelPackage.isEmpty()) {
            throw new NotFoundException("Packages not available with name: " + budgetRequest.getPackageName());
        }

        if (budgetRequest.getNumberOfDays() <= 0) {
            throw new NotFoundException("Days must be greater than 0");
        }

        if (budgetRequest.getNumberOfTravelers() <= 0) {
            throw new NotFoundException("Travelers must be greater than 0");
        }

        TravelPackage foundTravelPackage = optionalTravelPackage.get();
        Integer numberOfDays = budgetRequest.getNumberOfDays();
        Integer numberOfTravelers = budgetRequest.getNumberOfTravelers();
        Double packagePrice = foundTravelPackage.getPrice();
        Double totalBudget = (packagePrice * numberOfTravelers) * numberOfDays;

        return BudgetResponse.builder()
                .city(budgetRequest.getCity())
                .hotelName(foundTravelPackage.getHotelName())
                .packageName(foundTravelPackage.getPackageName())
                .numberOfDays(numberOfDays)
                .numberOfTravelers(numberOfTravelers)
                .totalBudget(totalBudget)
                .build();
    }
}