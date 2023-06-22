package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    public AuthController(TokenService tokenService){
        this.tokenService = tokenService;
    }

    @Autowired
    private UserService userService;

    @PostMapping("/api/auth/login")
    public String token(Authentication authentication){
        //Aller vérifier le login en BDD
        LOG.debug("Token requested for user : '{}'", authentication.getName());
        String token = tokenService.generateToken(authentication.getName());
        LOG.debug("Token granted {}", token);
        return token;
    }

    @PostMapping("/api/auth/register") //Create a new user
    public String createUser(@RequestBody User user) {
        //Ici rechercher des annotations pour contrôle des champs obligatoires

        User userCreated = userService.createUser(user);
        if(userCreated != null) {
            LOG.debug("Token requested for user : '{}'", user.getName());
            String token = tokenService.generateToken(user.getName());
            LOG.debug("Token granted {}", token);
            return token;
        } else {
            return "error";
        }
    }

}
