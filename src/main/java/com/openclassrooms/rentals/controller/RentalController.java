package com.openclassrooms.rentals.controller;


import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;

    @Operation(summary = "Get all rentals", description = "Return all rentals")
    @GetMapping("/api/rentals")
    public Iterable<Rental> getAllRentals(){
            return rentalService.getAll();
    }
}
