package com.example.clientcleaningcenter.model;

public class LoginRequest {

    private String username; //Alte LoginPrinzip ohne Token
    private String password;

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}