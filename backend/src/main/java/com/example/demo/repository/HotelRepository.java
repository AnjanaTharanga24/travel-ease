package com.example.demo.repository;

import com.example.demo.model.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HotelRepository extends MongoRepository<Hotel,String> {

    Hotel findByCity(String city);
    Hotel findByHotelNames(String name);
}
