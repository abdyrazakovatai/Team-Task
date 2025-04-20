package com.example.chatapp.api;

import com.example.chatapp.entity.User;
import com.example.chatapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public String getAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "getAll";
    }

    @GetMapping("/register")
    public String signUp(@ModelAttribute User user, Model model) {
        model.addAttribute("user",user);
        return "register";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute User user, HttpSession session) {
        session.setAttribute("user",user);
        userService.save(user);
        return "redirect:/api/user/users";
    }
}
