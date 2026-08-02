package com.example.restapi.model;

public class LoginResponse {

    private String token;
    private String tokenType;
    private String username;

    public LoginResponse() {
    }

    public LoginResponse(String token, String tokenType, String username) {
        this.token = token;
        this.tokenType = tokenType;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getUsername() {
        return username;
    }
}