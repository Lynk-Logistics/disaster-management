package com.guru.sishyan.service;

import com.guru.sishyan.models.Place;
import com.guru.sishyan.models.Supply;
import com.guru.sishyan.models.Volunteer;
import com.guru.sishyan.repository.PlaceRepository;
import com.guru.sishyan.repository.SupplyRepository;
import com.guru.sishyan.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplyDemandListener {
    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    PlaceRepository placeRepository;
    @KafkaListener(topics = "supply-demand", groupId = "group_id")
    public void getMessage(String msg) {
        List<Supply> supplies = supplyRepository.findByIsProcessed(false);
        if(supplies.size() != 0) {
            Supply supply = supplies.get(0);
            Point p = new Point(supply.getGeoLocation().getX(), supply.getGeoLocation().getY());
            List<Place> hubs = placeRepository.findByIsAvalableAndCoordinateNear(false, p, new Distance(10, Metrics.KILOMETERS));
            List<Volunteer> l = volunteerRepository.findByIsAvailableAndCoordinateNear(true, p, new Distance(10, Metrics.KILOMETERS));
            if (l.size() >= supply.getNumberOfPeople()) {
                for (Volunteer v : l) {
                    v.setIsAvailable(false);
                    v.setSupplyId(supply.getId());
                    v.setHubId(hubs.get(0).getId());
                }
                volunteerRepository.saveAll(l);
                supply.setIsProcessed(true);
            }
            supplyRepository.save(supply);
        }
    }
}
