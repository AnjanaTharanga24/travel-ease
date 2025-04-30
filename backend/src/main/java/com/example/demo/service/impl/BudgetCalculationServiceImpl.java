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

        List<TravelPackage> travelPackage = travelPackageRepository.findAllByHotelName(budgetRequest.getHotelName());

        if (travelPackage == null){
            throw new NotFoundException("that hotel name not included in our system");
        }

        Optional<TravelPackage> optionalTravelPackage = travelPackageRepository.findByHotelNameAndPackageName(budgetRequest.getHotelName(),budgetRequest.getPackageName());

        if (!optionalTravelPackage.isPresent()){
            throw new NotFoundException("packages not available with name : " + budgetRequest.getPackageName());
        }

        TravelPackage foundTravelPackage = optionalTravelPackage.get();

        Integer numberOfDays = budgetRequest.getNumberOfDays();
        Integer numberOfTravelers = budgetRequest.getNumberOfTravelers();
        Double packagePrice = foundTravelPackage.getPrice();

        Double Budget = (packagePrice * numberOfTravelers ) * numberOfDays;

        return BudgetResponse.builder()
                .city(budgetRequest.getCity())
                .hotelName(foundTravelPackage.getHotelName())
                .packageName(foundTravelPackage.getPackageName())
                .numberOfDays(numberOfDays)
                .numberOfTravelers(numberOfTravelers)
                .totalBudget(Budget)
                .build();
    }
}
