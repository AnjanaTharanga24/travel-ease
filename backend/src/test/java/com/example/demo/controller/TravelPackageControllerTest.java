package com.example.demo.controller;

import com.example.demo.exception.NotFoundException;
import com.example.demo.model.TravelPackage;
import com.example.demo.service.TravelPackageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TravelPackageController.class)
class TravelPackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelPackageService travelPackageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTravelPackage_ShouldReturnCreatedPackage() throws Exception {
        TravelPackage packageToCreate = new TravelPackage();
        packageToCreate.setDestination("Paris");
        packageToCreate.setHotelName("Grand Hotel");
        packageToCreate.setPackageName("Paris Deluxe");
        packageToCreate.setPrice(999.99);

        TravelPackage createdPackage = new TravelPackage();
        createdPackage.setId("1");
        createdPackage.setDestination("Paris");
        createdPackage.setHotelName("Grand Hotel");
        createdPackage.setPackageName("Paris Deluxe");
        createdPackage.setPrice(999.99);

        when(travelPackageService.createTravelPackage(any(TravelPackage.class))).thenReturn(createdPackage);

        mockMvc.perform(post("/packages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(packageToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.destination").value("Paris"));
    }

    @Test
    void getTravelPackagesByCity_ShouldReturnPackages() throws Exception {
        TravelPackage package1 = new TravelPackage();
        package1.setId("1");
        package1.setDestination("Paris");

        TravelPackage package2 = new TravelPackage();
        package2.setId("2");
        package2.setDestination("Paris");

        List<TravelPackage> packages = Arrays.asList(package1, package2);

        when(travelPackageService.getPackagesByCity("Paris")).thenReturn(packages);

        mockMvc.perform(get("/packages/city/Paris"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));
    }

    @Test
    void getTravelPackagesByCity_WhenNotFound_ShouldReturnNotFound() throws Exception {
        when(travelPackageService.getPackagesByCity("UnknownCity"))
                .thenThrow(new NotFoundException("No packages found"));

        mockMvc.perform(get("/packages/city/UnknownCity"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTravelPackageByHotelName_ShouldReturnPackages() throws Exception {
        TravelPackage package1 = new TravelPackage();
        package1.setId("1");
        package1.setHotelName("Grand Hotel");

        List<TravelPackage> packages = Arrays.asList(package1);

        when(travelPackageService.getPackageByHotelName("Grand Hotel")).thenReturn(packages);

        mockMvc.perform(get("/packages/hotel/Grand Hotel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[0].hotelName").value("Grand Hotel"));
    }
}