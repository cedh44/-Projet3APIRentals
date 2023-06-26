package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;
import java.util.Map;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Autowired
    private UserService userService;
    @Operation(summary = "Login", description = "Allow a user to log in and return a token")
    @PostMapping("/api/auth/login")
    public String login(@RequestBody User user){
        User userFound = userService.loginUser(user);
        if(userFound != null) {
            LOG.debug("Token requested for user : '{}'", userFound.getEmail());
            String token = tokenService.generateToken(userFound.getEmail());
            LOG.debug("Token granted {}", token);
            return token;
        } else {
            return "error";
        }
    }
    @Operation(summary = "Register a new user", description = "Check if user exists, create a user and return a token")
    @PostMapping("/api/auth/register") //Create a new user
    public String createUser(@RequestBody User user) {
        if (userService.findUserByEmail(user.getEmail()) != null) return "error"; //User exist
        else {
            User userCreated = userService.createUser(user);
            if (userCreated != null) {
                LOG.debug("Token requested for user : '{}'", user.getEmail());
                String token = tokenService.generateToken(user.getEmail());
                LOG.debug("Token granted {}", token);
                return token;
            } else {
                return "error";
            }
        }
    }

    @Operation(summary = "Get user informations", description = "return id, name, email, date creation and date update of user connected")
    @GetMapping("/api/auth/me") //Decode token, find user in BDD and return id, name, email, created_at and updated_at
    public User getUser(@RequestBody Authentication authentication) {
        String email = tokenService.decodeToken(authentication);
        return userService.findUserByEmail(email);
    }

}
