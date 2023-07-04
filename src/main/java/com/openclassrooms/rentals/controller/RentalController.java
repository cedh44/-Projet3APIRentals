package com.openclassrooms.rentals.controller;


import com.openclassrooms.rentals.dto.RentalDto;
import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.service.DocumentStorageService;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private DocumentStorageService documentStorageService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Get all rentals", description = "Return all rentals")
    @GetMapping("/api/rentals")
    public Iterable<Rental> getAllRentals(@RequestHeader("Authorization") String token) {
        return rentalService.getAll(); //Return all rentals from database
    }

    @Operation(summary = "Get rental by Id", description = "Return rental by Id")
    @GetMapping("/api/rentals/{id}")
    public Rental getRentalById(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
        return rentalService.getRental(id); //return rental by its Id
    }

    @Operation(summary = "Create rental", description = "Create rental with parameters (name, surface, price, picture and description) and owner_id from token")
    @PostMapping("/api/rentals")
    public Rental createRental(@RequestHeader("Authorization") String token, @ModelAttribute RentalDto rentalDto) throws IOException {
        //Save file and get path and file name
        String pathAndFileName = "";
        if (rentalDto.getPicture() != null) pathAndFileName = documentStorageService.storeFile(rentalDto.getPicture());
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token);
        Rental rentalToCreate = convertToEntity(rentalDto);
        return rentalService.createRental(userService.findUserByEmail(email).getId(), pathAndFileName, rentalToCreate); //Create rental with id and rentalToCreate
    }

    @Operation(summary = "Update rental", description = "Update rental by Id in url and  with parameters(name, surface, price, picture and description) and owner_id from token")
    @PutMapping("/api/rentals/{id}")
    public Rental updateRentalById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token, @ModelAttribute RentalDto rentalDto) throws IOException {
        //Save file and get path and file name
        String pathAndFileName = "";
        if (rentalDto.getPicture() != null) pathAndFileName = documentStorageService.storeFile(rentalDto.getPicture());
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token);
        return rentalService.updateRental(id, userService.findUserByEmail(email).getId(), pathAndFileName, convertToEntity(rentalDto)); //Update rental in database
    }


    private Rental convertToEntity(RentalDto rentalDto) {
        return modelMapper.map(rentalDto, Rental.class);
    }
}
