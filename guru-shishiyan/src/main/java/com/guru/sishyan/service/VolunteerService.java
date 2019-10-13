package com.guru.sishyan.service;

import com.guru.sishyan.models.Volunteer;
import com.guru.sishyan.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {
    @Autowired
    VolunteerRepository volunteerRepository;

    public List<Volunteer> getAvailabileVolunteers() {
        return volunteerRepository.findByIsAvailable(true);
    }
}
