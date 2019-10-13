package com.assist.api.services

import com.assist.api.models.LocationsModel
import com.assist.api.repositories.LocationsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LocationService {

    @Autowired
    private LocationsRepository locationsRepository

    List<LocationsModel> getAllLocations(){
        locationsRepository.findAll()
    }
}
