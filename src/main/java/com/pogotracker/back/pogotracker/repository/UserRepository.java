package com.pogotracker.back.pogotracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pogotracker.back.pogotracker.entity.User;


public interface UserRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}