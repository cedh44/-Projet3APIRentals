package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.model.User;
import com.openclassrooms.rentals.service.MessageService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Message", description = "Send a message with parameters : rental_id, user_id and message")
    @PostMapping("/api/messages")
    public Message createMessage(@RequestHeader("Authorization") String token, @RequestBody Message message){
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token); //Get email from token
        User user = userService.findUserByEmail(email); //Find user
        Message messageCreated = messageService.createMessage(user.getId(), message); //Get message
        return Objects.requireNonNullElseGet(messageCreated, Message::new); //Return message or empty message (if not found)
    }
}
