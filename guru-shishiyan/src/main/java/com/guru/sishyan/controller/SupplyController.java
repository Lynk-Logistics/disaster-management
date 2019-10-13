package com.guru.sishyan.controller;

import com.guru.sishyan.models.Coordinate;
import com.guru.sishyan.models.Supply;
import com.guru.sishyan.service.GeoService;
import com.guru.sishyan.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/supply")
public class SupplyController {
    @Autowired
    SupplyService supplyService;

    @PostMapping("/")
    public void add(@RequestBody Supply supply) {
        Double[] latlon = GeoService.getLatLong(supply.getAddress());
        GeoJsonPoint point = new GeoJsonPoint(latlon[0],latlon[1]);
        supply.setGeoLocation(point);
        supplyService.add(supply);
    }

    @GetMapping("/all")
    public List<Supply> getAll() {
        return supplyService.getAll();
    }
}
