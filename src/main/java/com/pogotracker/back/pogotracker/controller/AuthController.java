package com.pogotracker.back.pogotracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pogotracker.back.pogotracker.entity.User;
import com.pogotracker.back.pogotracker.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin()
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        User user = authService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return "Login successful";
        } else {
            return "Login failed";
        }
    }

    @PostMapping("/register")
    public String register(@RequestBody User username) {
        User existingUser = authService.findByUsername(username.getUsername());
        if (existingUser != null) {
            return "User already exists";
        } else {
            User newUser = new User(username.getUsername(), username.getPassword());
            authService.saveUser(newUser);
            return "User registered successfully";
        }
    }
    
}
