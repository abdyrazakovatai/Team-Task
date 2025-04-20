package com.example.chatapp.dto.response;

import org.springframework.http.HttpStatus;

public class AuthResponse {
    HttpStatus status;
    private String email;
    private String message;
}
