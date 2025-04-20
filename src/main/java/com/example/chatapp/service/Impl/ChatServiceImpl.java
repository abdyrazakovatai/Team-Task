package com.example.chatapp.service.Impl;

import com.example.chatapp.entity.Chat;
import com.example.chatapp.entity.User;
import com.example.chatapp.repository.ChatRepository;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public Chat chatStart(User currentUser, Long userId) {
        User chatUser = userRepository.getUserById(userId);
        Chat chat = new Chat();
        chat.getUsers().add(chatUser);
        chat.getUsers().add(currentUser);
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(
                ()-> new RuntimeException("Not found chat with id: " + chatId)
        );
    }
}
