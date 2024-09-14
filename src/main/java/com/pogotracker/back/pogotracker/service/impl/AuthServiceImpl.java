package com.pogotracker.back.pogotracker.service.impl;

import org.springframework.stereotype.Service;

import com.pogotracker.back.pogotracker.entity.User;
import com.pogotracker.back.pogotracker.repository.UserRepository;
import com.pogotracker.back.pogotracker.services.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	
	private UserRepository userRepository;

	public AuthServiceImpl( UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

}