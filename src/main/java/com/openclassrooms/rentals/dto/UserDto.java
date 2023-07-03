package com.openclassrooms.rentals.dto;

import com.openclassrooms.rentals.model.User;

public class UserDto {
    private String name;
    private String email;
    private String password;

    // standard getters and setters except for password

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return ""; //Security : email should never be returned to client !!!
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
