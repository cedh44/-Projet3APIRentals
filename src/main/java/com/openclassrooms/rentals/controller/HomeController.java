package com.openclassrooms.rentals.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

//TODO : à voir si à garder à la fin du projet

@RestController
public class HomeController {

    @GetMapping("/api/")
    public String home(Principal principal){
        return "Hello " + principal.getName();
    }
}
