package com.pogotracker.back.pogotracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pogotracker.back.pogotracker.entity.User;
import com.pogotracker.back.pogotracker.repository.UserRepository;

@Service
public class AuthService {

	 @Autowired
	    private UserRepository userRepository;

	    public User findByUsername(String username) {
	        return userRepository.findByUsername(username);
	    }

	    public User saveUser(User user) {
	        return userRepository.save(user);
	    }

	
}