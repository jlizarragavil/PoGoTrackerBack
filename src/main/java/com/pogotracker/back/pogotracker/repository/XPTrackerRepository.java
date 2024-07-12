package com.pogotracker.back.pogotracker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.pogotracker.back.pogotracker.entity.XPTracker;

public interface XPTrackerRepository extends MongoRepository<XPTracker, String> {
}