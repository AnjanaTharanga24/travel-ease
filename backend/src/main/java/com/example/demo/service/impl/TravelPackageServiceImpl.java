package com.example.demo.service.impl;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Hotel;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.TravelPackageRepository;
import com.example.demo.service.TravelPackageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TravelPackageServiceImpl implements TravelPackageService {

    private final TravelPackageRepository travelPackageRepository;
    private final HotelRepository hotelRepository;

    @Override
    public TravelPackage createTravelPackage(TravelPackage travelPackage) throws NotFoundException {
        String city = travelPackage.getDestination();
        String hotelName = travelPackage.getHotelName();

        Hotel optionalHotel = hotelRepository.findByCity(city);
        Hotel foundHotel = hotelRepository.findByHotelNames(hotelName);

        if (optionalHotel == null) {
            throw new NotFoundException(city + " is not tourism city");
        }

        if (foundHotel == null) {
            throw new NotFoundException(hotelName + " is not matching with our data");
        }

        TravelPackage newPackage = new TravelPackage();
        newPackage.setDestination(city);
        newPackage.setHotelName(hotelName);
        newPackage.setPackageName(travelPackage.getPackageName());
        newPackage.setDescription(travelPackage.getDescription());
        newPackage.setPrice(travelPackage.getPrice());

        return travelPackageRepository.save(newPackage);
    }

    @Override
    public List<TravelPackage> getPackagesByCity(String city) throws NotFoundException {
        List<TravelPackage> foundPackages = travelPackageRepository.findAllByDestinationIgnoreCase(city);

        if (foundPackages == null || foundPackages.isEmpty()) {
            throw new NotFoundException("there are no packages in " + city);
        }
        return foundPackages;
    }

    @Override
    public List<TravelPackage> getPackageByHotelName(String name) throws NotFoundException {
        List<TravelPackage> foundPackages = travelPackageRepository.findAllByHotelNameIgnoreCase(name);

        if (foundPackages == null || foundPackages.isEmpty()) {
            throw new NotFoundException("there are no packages for " + name);
        }
        return foundPackages;
    }

    @Override
    public List<TravelPackage> getPackageByCityAndHotel(String city, String hotelName) throws NotFoundException {
        List<TravelPackage> travelPackages = travelPackageRepository.findAllByDestinationAndHotelName(city,hotelName);

        if (travelPackages.isEmpty()){
            throw new NotFoundException("packages not found");
        }
        return travelPackages;
    }
}