package com.openclassrooms.rentals.dto;

public class GenericMessageDto {

    private String message;

    public GenericMessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
