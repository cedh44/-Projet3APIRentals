package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.repository.RentalRepository;
import com.openclassrooms.rentals.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    public Rental getRental(Long id){
        return rentalRepository.findById(id).orElse(null);
    }

    //Create rental
    public Rental createRental(Rental rental){
        rental.setCreatedAt(new Date());
        return rentalRepository.save(rental);
    }

    //Update rental
    public Rental updateRental(Long id, Rental rental){
        if (rentalRepository.existsById(id)) { //Check if exists
            rental.setId(id);
            rental.setUpdatedAt(new Date()); //Update date as today
            return rentalRepository.save(rental);
        } else {
            return new Rental();
        }
    }


}
