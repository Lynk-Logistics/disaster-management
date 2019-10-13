package com.guru.sishyan.controller;

import com.guru.sishyan.models.*;
import com.guru.sishyan.repository.HubRepository;
import com.guru.sishyan.repository.PlaceRepository;
import com.guru.sishyan.repository.SupplyRepository;
import com.guru.sishyan.repository.VolunteerRepository;
import com.guru.sishyan.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Optional;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@RestController
public class ResourceManagement {

    @Autowired
    HubRepository hubRepository;

    @Autowired
    SupplyRepository supplyRepository;

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    VolunteerRepository volunteerRepository;

    @RequestMapping(value= "/addVolunteer", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addVolunteer(@RequestBody Volunteer volunteer, HttpServletResponse httpServletResponse) {
        volunteer.setRole("VOLUNTEER");
        Double[] latlon = GeoService.getLatLong(volunteer.getLocation());
        GeoJsonPoint coordinate = new GeoJsonPoint(latlon[0],latlon[1]);
        volunteer.setCoordinate(coordinate);
        volunteerRepository.save(volunteer);
        Cookie cookie = new Cookie("username", volunteer.getUsername());
        httpServletResponse.addCookie(cookie);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<User> validateUser(@RequestBody User user, HttpServletResponse httpServletResponse) {
        User validatedUser = volunteerRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if(validatedUser != null) {
            Cookie cookie = new Cookie("username", validatedUser.getUsername());
            //add cookie to response
            httpServletResponse.addCookie(cookie);
        }
        return ok(validatedUser);
    }


    @RequestMapping(value = "/updateStatus", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity updateVolunteer(@RequestBody Volunteer volunteer) {
        Volunteer validatedVolunteer = volunteerRepository.findByUsername(volunteer.getUsername());
        Double[] latlon = GeoService.getLatLong(volunteer.getLocation());
        GeoJsonPoint coordinate = new GeoJsonPoint(latlon[0],latlon[1]);
        validatedVolunteer.setCoordinate(coordinate);
        validatedVolunteer.setIsAvailable(volunteer.getIsAvailable());
        Supply supply = supplyRepository.findById(validatedVolunteer.getSupplyId()).orElseThrow(null);
        int count = supply.getNumberOfPeople() - 1;
        if(count == 0) {
            supply.setIsProcessed(true);
            Hub hub = hubRepository.findById(validatedVolunteer.getHubId()).orElse(null);
            Map<String,Integer> map = (hub.getResourceDetails() != null) ? hub.getResourceDetails() : new HashMap<String,Integer>();
            Map<String,Integer> supplyMap = supply.getResourceDetails();
            for(Map.Entry<String,Integer> entry : supplyMap.entrySet()){
                if(map.get(entry.getKey()) != null){
                    map.put(entry.getKey(),entry.getValue() + map.get(entry.getKey()));
                }
                else
                    map.put(entry.getKey(),entry.getValue());
            }
            hub.setResourceDetails(map);
            hubRepository.save(hub);
        }
        else{
            supply.setNumberOfPeople(count);
        }
        supplyRepository.save(supply);
        kafkaTemplate.send("supply-demand","User is online");
        return ok(volunteerRepository.save(validatedVolunteer));
    }

    @RequestMapping(value = "/volunteerdetails", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<VolunteerDetails> userDetails(@RequestParam String username) {
        Volunteer volunteer = volunteerRepository.findByUsername(username);
        Optional<Supply> supply = Optional.empty();
        Optional<Hub> hub = Optional.empty();
        Optional<Place> place = Optional.empty();
        VolunteerDetails details = new VolunteerDetails();
        if(volunteer.getSupplyId() != null) {
            supply = supplyRepository.findById(volunteer.getSupplyId());

            if (volunteer.getHubId() != null) {
                hub = hubRepository.findById(volunteer.getHubId());
                place = placeRepository.findById(hub.get().getPlaceId());
            }
            details.setUserName(volunteer.getUsername());
            details.setSupplyLocation(supply.get().getAddress());
            details.setPhoneNumber(supply.get().getPhoneNumber());
            if (place.isPresent()) {
                details.setHubLocation(place.get().getAddress());
                details.setHubId(hub.get().getId());
                details.setSupplyId(supply.get().getId());
            }

        }
        details.setIsActive( volunteer.getIsAvailable() );
        return ok(details);
    }
}
