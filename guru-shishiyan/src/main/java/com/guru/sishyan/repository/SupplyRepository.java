package com.guru.sishyan.repository;

import com.guru.sishyan.models.Supply;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SupplyRepository  extends MongoRepository<Supply, String> {
    public List<Supply> findByIsProcessed(Boolean isProcessed);
}
