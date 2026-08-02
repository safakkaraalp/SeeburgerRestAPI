package com.example.restapi.controller;

import com.example.restapi.model.LoginRequest;
import com.example.restapi.model.LoginResponse;
import com.example.restapi.security.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        if (loginRequest == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Login-Daten fehlen.");
        }

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username != null) {
            username = username.trim();
        }

        if (password != null) {
            password = password.trim();
        }

        if ("admin".equals(username) && "1234".equals(password)) {
            String token = jwtService.generateToken(username);

            System.out.println("LOGIN ERFOLGREICH");
            System.out.println("Benutzer: " + username);
            System.out.println("JWT Token erzeugt: " + token);

            LoginResponse response = new LoginResponse(
                    token,
                    "Bearer",
                    username
            );

            return ResponseEntity.ok(response);
        }

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body("Benutzername oder Passwort falsch.");
    }
}