package com.example.chatapp.service.Impl;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.java.enums.Role;
import peaksoft.java.exception.BadRequestException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@gmail.com");
        user.setPassword("Admin123");
        if (userRepository.existsUserByEmail(user.getEmail())){
            userRepository.save(user);
        }else {
            throw new BadRequestException("User already exists");
        }
    }

    @Override
    public User save(User user) {
        User savedUser = new User();
        savedUser.setName(user.getName());
        savedUser.setUsername(user.getUsername());
        savedUser.setPassword(user.getPassword());
        savedUser.setEmail(user.getEmail());

        userRepository.save(user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
