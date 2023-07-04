package com.openclassrooms.rentals.controller;

import com.openclassrooms.rentals.dto.MessageDto;
import com.openclassrooms.rentals.model.Message;
import com.openclassrooms.rentals.service.MessageService;
import com.openclassrooms.rentals.service.TokenService;
import com.openclassrooms.rentals.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ModelMapper modelMapper;

    @Operation(summary = "Message", description = "Send a message with parameters : rental_id, user_id and message")
    @PostMapping("/api/messages")
    public MessageDto createMessage(@RequestHeader("Authorization") String token, @RequestBody MessageDto messageDto) {
        //Get user from token and Users table
        String email = tokenService.getEmailFromToken(token); //Get email from token
        return convertToDto(messageService.createMessage(userService.findUserByEmail(email).getId(), convertToEntity(messageDto))); //Get message
    }

    private MessageDto convertToDto(Message message) {
        return modelMapper.map(message, MessageDto.class);
    }

    private Message convertToEntity(MessageDto messageDto) {
        return modelMapper.map(messageDto, Message.class);
    }
}
