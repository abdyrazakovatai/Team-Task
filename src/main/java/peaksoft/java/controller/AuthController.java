package peaksoft.java.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import peaksoft.java.dto.request.AuthRequest;
import peaksoft.java.entity.User;
import peaksoft.java.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    final UserService userService;

    @GetMapping("/users")
    public String getAll(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users",users);
        return "getAll";
    }

    @GetMapping("/")
    private String main(@ModelAttribute AuthRequest user,Model model) {
        model.addAttribute("user",user);
        return "/index";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute AuthRequest user, HttpSession session) {
        session.setAttribute("user",user);
        userService.register(user);
        return "redirect:/api/auth/users";
    }
}
