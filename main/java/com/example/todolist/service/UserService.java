package com.example.todolist.service;

import com.example.todolist.dto.UserRegistrationDto;
import com.example.todolist.model.User;

public interface UserService {
    User saveUser(UserRegistrationDto registrationDto);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    User findByUsername(String username);
}