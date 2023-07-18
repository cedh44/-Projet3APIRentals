package com.openclassrooms.rentals.dto;

import javax.validation.constraints.NotBlank;
import java.util.Date;

public class RegisterDto {
    @NotBlank(message = "name is mandatory")
    private String name;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    private String password;

    // standard getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
