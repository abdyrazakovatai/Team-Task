package com.example.chatapp.service;

import com.example.chatapp.entity.Chat;
import com.example.chatapp.entity.User;

public interface ChatService {
    Chat chatStart(User currentUser, Long userId);

    Chat getChatById(Long userId);
}
