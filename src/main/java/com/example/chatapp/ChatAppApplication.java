package com.example.chatapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.chatapp")
public class ChatAppApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatAppApplication.class, args);
    }
}
