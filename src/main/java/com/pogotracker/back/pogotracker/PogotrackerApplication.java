package com.pogotracker.back.pogotracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class PogotrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PogotrackerApplication.class, args);
	}

}
