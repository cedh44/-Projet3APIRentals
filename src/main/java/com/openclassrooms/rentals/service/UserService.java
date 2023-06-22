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
    public User createUser(User user) {
        user.setCreatedAt(new Date()); //Positionne la date du jour
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    //Pour le login, faire un find
    public User findUser(User user){
        return userRepository.findByEmail(user.getEmail());
    }

}
