package com.guru.sishyan.repository;

import com.guru.sishyan.models.User;
import com.guru.sishyan.models.Volunteer;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VolunteerRepository extends MongoRepository<Volunteer,String> {
    public Volunteer findByUsernameAndPasswordAndRole(String username, String password,String role);
    public User findByUsernameAndPassword(String username,String password);
    public List<Volunteer> findByIsAvailable(Boolean isAvailable);
    List<Volunteer> findByIsAvailableAndCoordinateNear(Boolean isAvailable, Point p, Distance d);
    public Volunteer findByUsername(String username);
}