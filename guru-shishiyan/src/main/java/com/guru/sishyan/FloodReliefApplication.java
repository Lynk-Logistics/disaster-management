package com.guru.sishyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories
@EnableScheduling
public class FloodReliefApplication {
    public static void main(String args[]){
        SpringApplication.run(FloodReliefApplication.class,args);
    }
}
