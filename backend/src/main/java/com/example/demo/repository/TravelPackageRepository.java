package com.example.demo.repository;

import com.example.demo.model.TravelPackage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TravelPackageRepository extends MongoRepository<TravelPackage,String> {
    List<TravelPackage> findAllByDestination(String destination);
    List<TravelPackage> findAllByHotelName(String name);
    Optional<TravelPackage> findByHotelNameAndPackageName(String hotelName , String packName);
}
