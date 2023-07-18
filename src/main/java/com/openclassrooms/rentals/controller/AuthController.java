package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.LoginDto;
import com.openclassrooms.rentals.dto.RegisterDto;
import com.openclassrooms.rentals.dto.TokenDto;
import com.openclassrooms.rentals.dto.UserDto;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class AuthController {
    @Autowired TokenService tokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Login", description = "Allow a user to log in and return a token")
    @PostMapping("/api/auth/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginDto loginDto) {
        User user = new User();
        user.setEmail(loginDto.getEmail());
        user.setPassword(loginDto.getPassword());
        User userFound = userService.findUserByEmail(user.getEmail());
        if (userFound != null) {
            //Check if password from login is equal to password encoded in database
            if (!passwordEncoder.matches(user.getPassword(), userFound.getPassword()))
                return ResponseEntity.badRequest().body("error"); //Check if password from login is equal to password in database
            else return ResponseEntity.ok(new TokenDto(tokenService.generateToken(userFound.getEmail()))); //Get user and generate a token
        } else {
            return ResponseEntity.badRequest().body("error");
        }
    }

    @Operation(summary = "Register a new user", description = "Check if user exists, create a user and return a token")
    @PostMapping("/api/auth/register") //Create a new user
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterDto registerDto) {
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());
        if (userService.existsByEmail(user.getEmail())) return ResponseEntity.badRequest().body("error");; //User exist
        user.setPassword(passwordEncoder.encode(user.getPassword())); //Encode password
        User userCreated = userService.createUser(user); //Call service to create user in database
        if (userCreated != null) {
            return ResponseEntity.ok(new TokenDto(tokenService.generateToken(user.getEmail()))); //If user created, then generate token
        } else {
            return ResponseEntity.badRequest().body("error");
        }
    }

    @Operation(summary = "Get user informations", description = "return name, email of user connected")
    @GetMapping("/api/auth/me") //Decode token, find user in BDD and return id, name, email, created_at and updated_at
    public UserDto getUser(@RequestHeader("Authorization") String token) {
        String email = tokenService.getEmailFromToken(token); //Decode token and extract email
        UserDto userDto = convertToDto(userService.findUserByEmail(email));
        return convertToDto(userService.findUserByEmail(email)); //Conversion DTO
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setPassword(""); //Security : email should never be returned to client !!!
        return userDto;
    }

}
