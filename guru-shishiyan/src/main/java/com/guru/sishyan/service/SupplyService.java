package com.guru.sishyan.service;

import com.guru.sishyan.models.Hub;
import com.guru.sishyan.models.Place;
import com.guru.sishyan.models.Supply;
import com.guru.sishyan.models.Volunteer;
import com.guru.sishyan.repository.HubRepository;
import com.guru.sishyan.repository.PlaceRepository;
import com.guru.sishyan.repository.SupplyRepository;
import com.guru.sishyan.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import org.springframework.data.geo.Point;
import java.util.Date;
import java.util.List;

@Service
public class SupplyService {
    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    VolunteerRepository volunteerRepository;

    @Autowired
    HubRepository hubRepository;

    @Autowired
    PlaceRepository placeRepository;

    public void add(Supply supply) {
        supply = supplyRepository.save(supply);
        Point p = new Point(supply.getGeoLocation().getX(),supply.getGeoLocation().getY());
        List<Place> places = placeRepository.findByIsAvalableAndCoordinateNear(false,p,new Distance(10, Metrics.KILOMETERS));
        Hub hub = hubRepository.findByPlaceId(places.get(0).getId());
        List<Volunteer> l = volunteerRepository.findByIsAvailableAndCoordinateNear(true,p,new Distance(10, Metrics.KILOMETERS));
        if(l.size() >= supply.getNumberOfPeople()){
            for(Volunteer v : l) {
                v.setIsAvailable(false);
                v.setSupplyId(supply.getId());
                v.setHubId(hub.getId());
            }
            volunteerRepository.saveAll(l);
            supply.setIsProcessed(true);
            supplyRepository.save(supply);
        }
    }

    public List<Supply> getAll() {
        return supplyRepository.findAll();
    }

    public List<Supply> getUnProcessedSupplies() {
        return supplyRepository.findByIsProcessed(false);
    }
}
