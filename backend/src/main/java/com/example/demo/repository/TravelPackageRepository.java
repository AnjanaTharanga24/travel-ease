package com.example.demo.repository;

import com.example.demo.model.TravelPackage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TravelPackageRepository extends MongoRepository<TravelPackage,String> {
    List<TravelPackage> findAllByDestination(String destination);
    List<TravelPackage> findAllByHotelName(String name);
}
