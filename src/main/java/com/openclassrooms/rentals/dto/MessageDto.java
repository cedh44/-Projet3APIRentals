package com.openclassrooms.rentals.dto;

import javax.persistence.Column;

public class MessageDto {
    private Long rental_id;
    private String message;

    public Long getRental_id() {
        return rental_id;
    }

    public void setRental_id(Long rental_id) {
        this.rental_id = rental_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
