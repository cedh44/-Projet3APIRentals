package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.UserDto;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Login", description = "Allow a user to log in and return a token")
    @PostMapping("/api/auth/login")
    public String login(@RequestBody User user) {
        User userFound = userService.findUserByEmail(user.getEmail());
        if (userFound != null) {
            //Check if password from login is equal to password encoded in database
            if (!passwordEncoder.matches(user.getPassword(), userFound.getPassword()))
                return "error"; //Check if password from login is equal to password in database
            else return tokenService.generateToken(userFound.getEmail()); //Get user and generate a token
        } else {
            return "error";
        }
    }

    @Operation(summary = "Register a new user", description = "Check if user exists, create a user and return a token")
    @PostMapping("/api/auth/register") //Create a new user
    public String register(@RequestBody User user) {
        //Name is mandatory to create a user. For email and password, checked by @Column(nullable = false) in User class
        if (user.getName() == null) return "Name is mandatory";
        if (userService.existsByEmail(user.getEmail())) return "error"; //User exist
        user.setPassword(passwordEncoder.encode(user.getPassword())); //Encode password
        User userCreated = userService.createUser(user); //Call service to create user in database
        if (userCreated != null) {
            return tokenService.generateToken(user.getEmail()); //If user created, then generate token
        } else {
            return "error";
        }
    }

    @Operation(summary = "Get user informations", description = "return id, name, email, date creation and date update of user connected")
    @GetMapping("/api/auth/me") //Decode token, find user in BDD and return id, name, email, created_at and updated_at
    public UserDto getUser(@RequestHeader("Authorization") String token) {
        String email = tokenService.getEmailFromToken(token); //Decode token and extract email
        return convertToDto(userService.findUserByEmail(email)); //Conversion DTO
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}
