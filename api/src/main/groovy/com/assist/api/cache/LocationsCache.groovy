package com.assist.api.cache

import com.assist.api.models.LocationsModel
import com.assist.api.pogo.dto.LocationDto
import com.assist.api.services.LocationService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.annotation.PostConstruct
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

@Service
class LocationsCache {

    @Autowired
    private LocationService locationService

    private static final Logger logger = LoggerFactory.getLogger(LocationsCache)

    ReadWriteLock readWriteLock = new ReentrantReadWriteLock()
    private static Map<Long,LocationDto> locationList = [:]


    @PostConstruct
    private void loadLocations(){
        readWriteLock.writeLock().lock()
        try {
            List<LocationsModel> locations = locationService.allLocations
            locations.each {
                locationList[it.locationId] = new LocationDto(it)
            }
        }catch (any){
            logger.error('Error while updating locations cache',any)
        }finally{
            readWriteLock.writeLock().unlock()
        }
    }

    static LocationDto getLocationById(Long id){
        return locationList[id]
    }

    static List<LocationDto> getAllLocations(){
        return locationList.values().collect{it}
    }

}
