package com.example.chatapp.service;

import com.example.chatapp.entity.User;

import java.util.List;

public interface UserService {
    User save(User user);

    List<User> findAll();
}
