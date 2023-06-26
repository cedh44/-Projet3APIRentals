package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.MessageService;
import com.openclassrooms.rentals.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final TokenService tokenService;
    public MessageController(TokenService tokenService){
        this.tokenService = tokenService;
    }
    @Autowired
    private MessageService messageService;

    @Operation(summary = "Message", description = "Send a message with parameters : rental_id, user_id and message")
    @PostMapping("/api/messages")
    public Message createMessage(@RequestBody Message message){
        Message messageCreated = messageService.createMessage(message);
        if(messageCreated != null)  {
            return message;
        } else {
            return new Message();
        }
    }
}
