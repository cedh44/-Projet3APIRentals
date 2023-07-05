package com.openclassrooms.rentals.dto;

public class TokenDto {

    private String token;

    public TokenDto(String tokenDto) {
        this.token = tokenDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
