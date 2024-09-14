package com.pogotracker.back.pogotracker.services;

import com.pogotracker.back.pogotracker.entity.User;

public interface AuthService {
    User findByUsername(String username);

    User saveUser(User user);
}
