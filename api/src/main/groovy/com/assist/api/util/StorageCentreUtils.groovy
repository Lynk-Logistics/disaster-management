package com.assist.api.util

import com.assist.api.models.StorageCentreModel
import com.assist.api.pogo.request.StorageCentreSaveRequest

class StorageCentreUtils {

    static StorageCentreModel create(StorageCentreSaveRequest storageCentreSaveRequest,Long userId){
        StorageCentreModel storageCentreModel = new StorageCentreModel()
        storageCentreModel.centreId = storageCentreSaveRequest.centreId
        storageCentreModel.centreName = storageCentreSaveRequest.centreName
        storageCentreModel.locationId = storageCentreSaveRequest.locationId
        storageCentreModel.latitude = storageCentreSaveRequest.latitude
        storageCentreModel.longitude = storageCentreSaveRequest.longitude
        storageCentreModel.createdBy = userId
        return storageCentreModel
    }

    static StorageCentreModel merge(StorageCentreModel newModel,StorageCentreModel existingModel){
        StorageCentreModel storageCentreModel = new StorageCentreModel()
        storageCentreModel.centreId = newModel.centreId
        storageCentreModel.centreName = newModel.centreName ?: existingModel.centreName
        storageCentreModel.locationId = newModel.locationId ?: existingModel.locationId
        storageCentreModel.latitude = newModel.latitude ?: existingModel.locationId
        storageCentreModel.longitude = newModel.longitude ?: existingModel.longitude
        return storageCentreModel
    }

}
