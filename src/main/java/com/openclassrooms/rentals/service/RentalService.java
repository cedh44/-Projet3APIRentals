package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.repository.RentalRepository;
import com.openclassrooms.rentals.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@Data
@Service
public class RentalService {
    @Autowired
    private RentalRepository rentalRepository;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    //Return all rentals
    public Iterable<Rental> getAll(){
        return rentalRepository.findAll();
    }

    //Get rental by Id
    public Rental getRental(Long rentalId){
        return rentalRepository.findById(rentalId).orElse(null);
    }

    //Create rental
    public Rental createRental(Long ownerId, Rental rental){
        rental.setCreatedAt(new Date());
        rental.setOwner_id(ownerId);
        return rentalRepository.save(rental);
    }

    //Update rental
    public Rental updateRental(Long rentalId, Rental freshRental) throws IllegalAccessException {
        Rental rentalToUpdate = rentalRepository.findById(rentalId).orElse(null);
        if(rentalToUpdate != null){
            Field[] fieldsFreshRental = freshRental.getClass().getDeclaredFields();
            for (Field field : fieldsFreshRental) {
                Object freshValue = field.get(freshRental);
                if (freshValue != null) {
                    field.set(rentalToUpdate, freshValue);
                }
            }
            rentalToUpdate.setUpdatedAt(new Date()); //Update date as today
            return rentalRepository.save(rentalToUpdate);
        } else {
            return new Rental();
        }
    }


}
