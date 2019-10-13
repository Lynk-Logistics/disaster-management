package com.assist.api.controllers

import com.assist.api.cache.LocationsCache
import com.assist.api.pogo.dto.LocationDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/location')
class LocationController {

    @GetMapping('/')
    List<LocationDto> getAllLocations(){
        return LocationsCache.getAllLocations()
    }
}
