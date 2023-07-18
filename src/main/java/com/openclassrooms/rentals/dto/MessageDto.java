package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessageDto {
    @NotNull(message = "rental_id is mandatory")
    private Long rental_id;
    @NotBlank(message = "message is mandatory")
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
