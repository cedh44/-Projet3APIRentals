package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    //User creation
    public User createUser(User user) {
        user.setCreatedAt(new Date()); //Set today
        return userRepository.save(user); //Save user in database
    }

    //Find user with email as parameter
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email); //Find user by its email in database
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email); //Check id user exists in database
    }

}
