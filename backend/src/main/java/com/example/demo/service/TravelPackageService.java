package com.example.demo.service;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.TravelPackage;

import java.util.List;

public interface TravelPackageService {

    TravelPackage createTravelPackage(TravelPackage travelPackage) throws NotFoundException;
    List<TravelPackage> getPackagesByCity(String city) throws NotFoundException;
    List<TravelPackage> getPackageByHotelName(String name) throws NotFoundException;
}
