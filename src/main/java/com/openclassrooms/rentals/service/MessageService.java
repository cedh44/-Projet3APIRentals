package com.openclassrooms.rentals.service;

import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.repository.MessageRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;

@Data
@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    //Message creation
    public Message createMessage(Long userId, Message message) {
        message.setUser_id(userId);
        message.setCreatedAt(new Date()); //Positionne la date du jour
        return messageRepository.save(message);
    }




}
