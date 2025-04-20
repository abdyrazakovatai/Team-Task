package com.example.chatapp.service;

import com.example.chatapp.entity.Message;
import com.example.chatapp.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {
    Message saveMessage(Message message);

    User getUserById(Long userId);
}
