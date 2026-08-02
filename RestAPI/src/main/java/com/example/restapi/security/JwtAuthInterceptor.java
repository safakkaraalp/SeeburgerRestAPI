package com.example.restapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    public JwtAuthInterceptor(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        System.out.println("JWT Kontrolle gestartet für: " + request.getRequestURI());

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            System.out.println("JWT FEHLT");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("JWT Token fehlt.");
            return false;
        }

        String token = authorizationHeader.substring(7);

        System.out.println("Authorization Header erhalten.");
        System.out.println("Token Anfang: " + token.substring(0, Math.min(token.length(), 25)) + "...");

        if (!jwtService.isTokenValid(token)) {
            System.out.println("JWT UNGÜLTIG ODER ABGELAUFEN");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().write("JWT Token ist ungültig oder abgelaufen.");
            return false;
        }

        String username = jwtService.extractUsername(token);

        System.out.println("JWT GÜLTIG für Benutzer: " + username);

        return true;
    }
}