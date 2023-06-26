package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.repository.MessageRepository;
import com.openclassrooms.rentals.repository.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    //Message creation
    public Message createMessage(Message message) {
        message.setCreatedAt(new Date()); //Positionne la date du jour
        Message createdMessage = messageRepository.save(message);
        return createdMessage;
    }




}
