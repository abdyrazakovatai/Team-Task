package com.example.chatapp.repository;

import com.example.chatapp.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    Chat getChatById(Long id);
}
