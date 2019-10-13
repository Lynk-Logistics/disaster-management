package com.assist.api.services

import com.assist.api.models.StorageCentreModel
import com.assist.api.repositories.StorageCentreRepository
import com.assist.api.util.StorageCentreUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StorageCentreService {

    @Autowired
    private StorageCentreRepository storageCentreRepository

    StorageCentreModel save(StorageCentreModel storageCentreModel){
        if(storageCentreModel.centreId){
            StorageCentreModel existingModel = findById(storageCentreModel.centreId)
            return storageCentreRepository.save(StorageCentreUtils.merge(storageCentreModel,existingModel))
        }else{
            return storageCentreRepository.save(storageCentreModel)
        }
    }

    StorageCentreModel findById(long storageCentreId){
        storageCentreRepository.findById(storageCentreId).orElse(null)
    }

    List<StorageCentreModel> findByLocation(long locationId){
        storageCentreRepository.findByLocationId(locationId)
    }
}
