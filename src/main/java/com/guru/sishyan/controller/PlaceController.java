package com.guru.sishyan.controller;

import com.guru.sishyan.models.Coordinate;
import com.guru.sishyan.models.Hub;
import com.guru.sishyan.models.Place;
import com.guru.sishyan.repository.PlaceRepository;
import com.guru.sishyan.service.GeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlaceController {
    @Autowired
    PlaceRepository placeRepository;

    @RequestMapping(value= "place/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity addPlace(@RequestBody Place request){

        Double[] latlon = GeoService.getLatLong(request.getAddress());
        GeoJsonPoint point = new GeoJsonPoint(latlon[0],latlon[1]);
        request.setCoordinate(point);
        request.setIsAvalable(true);
        placeRepository.save(request);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value= "place/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity listAll(){
        List<Place> places = placeRepository.findByIsAvalable(true);
        return ResponseEntity.ok(places);
    }

}
