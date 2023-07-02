package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Autowired
    private UserService userService;

    @Operation(summary = "Login", description = "Allow a user to log in and return a token")
    @PostMapping("/api/auth/login")
    public String login(@RequestBody User user) {
        User userFound = userService.loginUser(user);
        if (userFound != null) {
            return tokenService.generateToken(userFound.getEmail());
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
        User userCreated = userService.createUser(user);
        if (userCreated != null) {
            return tokenService.generateToken(user.getEmail());
        } else {
            return "error";
        }
    }

    @Operation(summary = "Get user informations", description = "return id, name, email, date creation and date update of user connected")
    @GetMapping("/api/auth/me") //Decode token, find user in BDD and return id, name, email, created_at and updated_at
    public User getUser(@RequestHeader("Authorization") String token) {
        //Decode token and extract email
        String email = tokenService.getEmailFromToken(token);
        //Find user in database
        User user = userService.findUserByEmail(email);
        //Security  : set password to ""
        user.setPassword("");
        return user;
    }

}
