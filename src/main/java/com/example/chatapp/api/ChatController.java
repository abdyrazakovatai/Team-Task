package com.example.chatapp.api;

import com.example.chatapp.dto.request.MessageDto;
import com.example.chatapp.entity.Chat;
import com.example.chatapp.entity.Message;
import com.example.chatapp.entity.User;
import com.example.chatapp.exception.NotfoundException;
import com.example.chatapp.service.ChatService;
import com.example.chatapp.service.MessageService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final MessageService messageService;
    final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/startChat")
    public String startChat(@RequestParam Long userId, HttpSession session, Model model) {
        User currentUser = (User) session.getAttribute("user");
        System.out.println("currentUser = " + currentUser);
        System.out.println("userId = " + userId);

        Chat chat = chatService.chatStart(currentUser, userId);
        model.addAttribute("chat", chat.getId());
        model.addAttribute("user", currentUser.getId());
        return "chat/chatStart";
    }

    @Transactional
    @MessageMapping("/send")
    public Message sendMessage(MessageDto messageDto) {
        Message message = new Message();
        System.out.println("messageDto = " + messageDto);

        message.setContent(messageDto.getContent());
        Chat chat = chatService.getChatById(messageDto.getChatId());
        System.out.println("chat = " + chat);
        if (chat == null) {
            throw new NotfoundException("Chat with ID " + messageDto.getChatId() + " not found");
        }
        message.setChat(chat);
        User user = messageService.getUserById(messageDto.getUserId());
        System.out.println("user = " + user);

        if (user == null) {
            throw new NotfoundException("User with ID " + messageDto.getUserId() + " not found");
        }
        if (user.getName() == null) {
            user.setName(messageDto.getSender());
        }

        message.setUser(user);
        Message savedMessage = messageService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/messages/" + chat.getId(), savedMessage);
        return savedMessage;
    }
}