package com.example.chatapp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.ToString;

@Entity
@Table(name = "messages")
@ToString
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @ManyToOne
    @JsonProperty("chat")
    private Chat chat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonProperty("user")
    private User user;

    public Message() {
    }

    public Message(User user) {
        this.user = user;
    }

    public Message(Chat chat, User user) {
        this.chat = chat;
        this.user = user;
    }

    public Message(Long id, String content, Chat chat, User user) {
        this.id = id;
        this.content = content;
        this.chat = chat;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
