package com.openclassrooms.rentals.controller;


import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Objects;

@RestController
public class RentalController {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserService userService;

    @Operation(summary = "Get all rentals", description = "Return all rentals")
    @GetMapping("/api/rentals")
    public Iterable<Rental> getAllRentals(){
            return rentalService.getAll();
    }

    @Operation(summary = "Get rental by Id", description = "Return rental by Id")
    @GetMapping("/api/rentals/{id}")
    public Rental getRentalById(@PathVariable("id") Long id){
        return rentalService.getRental(id);
    }

    @Operation(summary = "Create rental", description = "Create rental with parameters (name, surface, price, picture and description) and owner_id from token")
    @PostMapping("/api/rentals")
    public Rental createRental(@RequestHeader("Authorization") String token, @ModelAttribute Rental rental){
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token);
        User user = userService.findUserByEmail(email);
        //Create rental with id and rental
        Rental rentalCreated = rentalService.createRental(user.getId(), rental);
        return Objects.requireNonNullElseGet(rentalCreated, Rental::new);
    }

    @Operation(summary = "Update rental", description = "Update rental by Id in url and  with parameters(name, surface, price, picture and description) and owner_id from token")
    @PutMapping("/api/rentals/{id}")
    public Rental updateRentalById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token, @ModelAttribute Rental rental){
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token);
        User user = userService.findUserByEmail(email);
        //Update rental in database
        Rental rentalUpdated = rentalService.updateRental(id, user.getId(), rental);
        //Return rental update, of new rental if KO
        return Objects.requireNonNullElseGet(rentalUpdated, Rental::new);
    }


}
