package com.example.todolist.controller;

import com.example.todolist.dto.UserRegistrationDto;
import com.example.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDto userDto,
                               BindingResult bindingResult,
                               Model model) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "register";
        }

        if (userService.existsByUsername(userDto.getUsername())) {
            model.addAttribute("usernameError", "Имя пользователя уже занято");
            return "register";
        }

        if (userService.existsByEmail(userDto.getEmail())) {
            model.addAttribute("emailError", "Email уже зарегистрирован");
            return "register";
        }

        userService.saveUser(userDto);
        return "redirect:/auth/login?success";
    }
}