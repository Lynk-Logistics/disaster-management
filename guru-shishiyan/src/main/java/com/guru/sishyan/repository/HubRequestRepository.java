package com.guru.sishyan.repository;


import com.guru.sishyan.models.HubRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HubRequestRepository  extends MongoRepository<HubRequest,String> {
}
