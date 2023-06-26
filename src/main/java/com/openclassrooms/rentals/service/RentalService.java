package com.openclassrooms.rentals.service;

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





}
