package com.openclassrooms.rentals.controller;


import com.openclassrooms.rentals.dto.GenericMessageDto;
import com.openclassrooms.rentals.dto.RentalDto;
import com.openclassrooms.rentals.dto.RentalsListDto;
import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.service.DocumentStorageService;
import com.openclassrooms.rentals.service.RentalService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

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

    @ApiOperation(value = "Get all rentals", notes= "Return all rentals", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping("/api/rentals")
    public RentalsListDto getAllRentals() {
        RentalsListDto rentalsListDto = new RentalsListDto();
        ArrayList<Rental> rentalList = new ArrayList<>();
        rentalService.getAll().forEach(rentalList::add);
        rentalsListDto.setRentals(rentalList);
        return rentalsListDto; //Return all rentals from database
    }


    @ApiOperation(value = "Get rental by Id", notes ="Return rental by Id", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping("/api/rentals/{id}")
    public Rental getRentalById(@PathVariable("id") Long id) {

        return rentalService.getRental(id); //return rental by its Id
    }

    @Operation(summary = "Create rental", description = "Create rental with parameters (name, surface, price, picture and description) and owner_id from token")
    @PostMapping(path = "/api/rentals", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericMessageDto> createRental(@RequestHeader("Authorization") String token, @Valid @ModelAttribute RentalDto rentalDto) throws IOException {
        String pathAndFileName = "";
        if (rentalDto.getPicture() != null)
            pathAndFileName = documentStorageService.storeFile(rentalDto.getPicture()); //Save file and get path and file name
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token);
        Rental rental = rentalService.createRental(userService.findUserByEmail(email).getId(), pathAndFileName, convertToEntity(rentalDto)); //Create rental in database
        if(rental != null) return ResponseEntity.ok(new GenericMessageDto("Rental created !"));
        else return ResponseEntity.badRequest().body(new GenericMessageDto("Error"));
    }

    @Operation(summary = "Update rental", description = "Update rental by Id in url and  with parameters(name, surface, price, picture and description) and owner_id from token")
    @PutMapping("/api/rentals/{id}")
    public ResponseEntity<GenericMessageDto> updateRentalById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token, @Valid @ModelAttribute RentalDto rentalDto) throws IOException {
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token);
        Rental rental = rentalService.updateRental(id, userService.findUserByEmail(email).getId(), convertToEntity(rentalDto)); //Update rental in database
        if(rental != null) return ResponseEntity.ok(new GenericMessageDto("Rental updated !"));
        else return ResponseEntity.badRequest().body(new GenericMessageDto("Error"));
    }


    private Rental convertToEntity(RentalDto rentalDto) {
        return modelMapper.map(rentalDto, Rental.class);
    }
}
