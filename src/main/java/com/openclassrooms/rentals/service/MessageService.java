package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.repository.MessageRepository;
import com.openclassrooms.rentals.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Data
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");





}
