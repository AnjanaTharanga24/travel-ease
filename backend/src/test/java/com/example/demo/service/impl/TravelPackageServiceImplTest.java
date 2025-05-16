package com.example.demo.service.impl;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Hotel;
import com.example.demo.model.TravelPackage;
import com.example.demo.repository.HotelRepository;
import com.example.demo.repository.TravelPackageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelPackageServiceImplTest {

    @Mock
    private TravelPackageRepository travelPackageRepository;

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private TravelPackageServiceImpl travelPackageService;


    @Test
    void getPackagesByCity_ShouldReturnPackages() throws NotFoundException {
        TravelPackage package1 = new TravelPackage();
        package1.setId("1");
        package1.setDestination("Paris");

        when(travelPackageRepository.findAllByDestinationIgnoreCase("Paris"))
                .thenReturn(Collections.singletonList(package1));

        List<TravelPackage> result = travelPackageService.getPackagesByCity("Paris");

        assertEquals(1, result.size());
        assertEquals("1", result.get(0).getId());
    }

    @Test
    void getPackagesByCity_WhenNoPackages_ShouldThrowException() {
        when(travelPackageRepository.findAllByDestinationIgnoreCase("EmptyCity"))
                .thenReturn(Collections.emptyList());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> travelPackageService.getPackagesByCity("EmptyCity"));

        assertEquals("there are no packages in EmptyCity", exception.getMessage());
        verify(travelPackageRepository, times(1)).findAllByDestinationIgnoreCase("EmptyCity");
    }

    @Test
    void getPackagesByCity_WithNullResult_ShouldThrowException() {
        when(travelPackageRepository.findAllByDestinationIgnoreCase("NullCity"))
                .thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> travelPackageService.getPackagesByCity("NullCity"));

        assertEquals("there are no packages in NullCity", exception.getMessage());
        verify(travelPackageRepository, times(1)).findAllByDestinationIgnoreCase("NullCity");
    }

    @Test
    void getPackageByHotelName_ShouldReturnPackages() throws NotFoundException {
        TravelPackage package1 = new TravelPackage();
        package1.setId("1");
        package1.setHotelName("Grand Hotel");
        package1.setPackageName("Luxury Package");

        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Grand Hotel"))
                .thenReturn(Collections.singletonList(package1));

        List<TravelPackage> result = travelPackageService.getPackageByHotelName("Grand Hotel");

        assertEquals(1, result.size());
        assertEquals("Grand Hotel", result.get(0).getHotelName());
    }

    @Test
    void getPackageByHotelName_WithEmptyResult_ShouldThrowException() {
        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Unknown Hotel"))
                .thenReturn(Collections.emptyList());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> travelPackageService.getPackageByHotelName("Unknown Hotel"));

        assertEquals("there are no packages for Unknown Hotel", exception.getMessage());
        verify(travelPackageRepository, times(1)).findAllByHotelNameIgnoreCase("Unknown Hotel");
    }

    @Test
    void getPackageByHotelName_WithNullResult_ShouldThrowException() {
        when(travelPackageRepository.findAllByHotelNameIgnoreCase("Null Hotel"))
                .thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> travelPackageService.getPackageByHotelName("Null Hotel"));

        assertEquals("there are no packages for Null Hotel", exception.getMessage());
        verify(travelPackageRepository, times(1)).findAllByHotelNameIgnoreCase("Null Hotel");
    }
}