package com.guru.sishyan.repository;

import com.guru.sishyan.models.Place;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlaceRepository extends MongoRepository<Place,String> {
    List<Place> findByIsAvalable(Boolean isAvailable);
    List<Place> findByIsAvalableAndCoordinateNear(Boolean isAvalable, Point p, Distance d);
}
