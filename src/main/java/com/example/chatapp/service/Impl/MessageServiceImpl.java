package com.example.chatapp.service.Impl;

import com.example.chatapp.entity.Message;
import com.example.chatapp.entity.User;
import com.example.chatapp.exception.NotfoundException;
import com.example.chatapp.repository.ChatRepository;
import com.example.chatapp.repository.MessageRepository;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public Message saveMessage(Message message) {
        if (message.getChat() == null) {
            throw new IllegalArgumentException("Chat cannot be null in the message.");
        }
//        Message savedMessage = new Message();
//        Chat chat = chatRepository.getChatById(message.getChat().getId());
//        if (chat != null) {
//            savedMessage.setChat(chat);
//            savedMessage.setUser(message.getUser());
//            savedMessage.setType(message.getType());
            return messageRepository.save(message);
//        } else {
//            throw new NotfoundException("Chat with " + message.getChat().getId() + " not found");
//        }
//        return message;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotfoundException("User with " + userId + " not found"));
    }
}
