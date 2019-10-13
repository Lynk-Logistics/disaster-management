package com.guru.sishyan.repository;

import com.guru.sishyan.models.Request;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestRepository extends MongoRepository<Request, String> {

}
