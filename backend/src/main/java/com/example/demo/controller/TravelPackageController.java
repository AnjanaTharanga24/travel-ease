package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.TravelPackage;
import com.example.demo.service.TravelPackageService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin("http://localhost:3000/")
public class TravelPackageController {

    private TravelPackageService travelPackageService;

    @PostMapping("/packages")
    public TravelPackage createTravelPackage(@RequestBody TravelPackage travelPackage) throws NotFoundException {
        return travelPackageService.createTravelPackage(travelPackage);
    }

    @GetMapping("/packages/city/{city}")
    public List<TravelPackage> getTravelPackagesByCity(@PathVariable("city") String city)throws NotFoundException{
        return travelPackageService.getPackagesByCity(city);
    }

    @GetMapping("/packages/hotel/{name}")
    public List<TravelPackage> getTravelPackageByHotelName(@PathVariable("name") String name)throws NotFoundException{
        return travelPackageService.getPackageByHotelName(name);
    }
}
