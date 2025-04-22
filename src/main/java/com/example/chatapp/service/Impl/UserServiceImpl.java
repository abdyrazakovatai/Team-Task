package com.example.chatapp.service.Impl;

import com.example.chatapp.entity.User;
import com.example.chatapp.repository.UserRepository;
import com.example.chatapp.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        boolean exists = userRepository.existsUserByEmail("admin@gmail.com");
        if (!exists) {
            userRepository.save(User.builder()
                    .name("admin")
                    .email("admin@gmail.com")
                    .password("Admin123")
                    .build());
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
