package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Rental;
import com.openclassrooms.rentals.repository.RentalRepository;
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
    public Rental getRental(Long rentalId){
        return rentalRepository.findById(rentalId).orElse(null);
    }

    //Create rental
    public Rental createRental(Long ownerId, String pathAndFileName, Rental rental){
        //Set create date
        rental.setCreatedAt(new Date());
        rental.setOwner_id(ownerId);
        rental.setPicture(pathAndFileName);
        return rentalRepository.save(rental);
    }

    //Update rental
    public Rental updateRental(Long rentalId, Long ownerId, String pathAndFileName, Rental freshRental) {
        Rental rentalToUpdate = rentalRepository.findById(rentalId).orElse(null);
        //Check if rentals in DBB and same owner
        if(rentalToUpdate != null && ownerId.equals(rentalToUpdate.getOwner_id())){
            //Update datas rental if new
            if(freshRental.getName()!=null) rentalToUpdate.setName(freshRental.getName());
            if(freshRental.getSurface()!=null) rentalToUpdate.setSurface(freshRental.getSurface());
            if(freshRental.getPrice() != null) rentalToUpdate.setPrice(freshRental.getPrice());
            if(freshRental.getPicture() != null) rentalToUpdate.setPicture(freshRental.getPicture());
            if(freshRental.getDescription() != null)rentalToUpdate.setDescription(freshRental.getDescription());
            //Set update date
            rentalToUpdate.setUpdatedAt(new Date());
            rentalToUpdate.setPicture(pathAndFileName);
            return rentalRepository.save(rentalToUpdate);
        } else {
            return new Rental();
        }
    }


}
