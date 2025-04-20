package com.example.chatapp.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class MessageDto {
    private Long id;
    private String content;
    private Long chatId;
    private Long userId;
    private String sender;
}
