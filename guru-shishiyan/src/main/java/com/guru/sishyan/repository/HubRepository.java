package com.guru.sishyan.repository;

import com.guru.sishyan.models.Hub;
import com.guru.sishyan.models.Place;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.awt.*;
import java.util.List;

public interface HubRepository extends MongoRepository<Hub,String> {
    public Hub findByPlaceId(String placeId);
}
