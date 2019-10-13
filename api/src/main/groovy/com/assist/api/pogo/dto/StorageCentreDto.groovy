package com.assist.api.pogo.dto

import com.assist.api.cache.LocationsCache
import com.assist.api.models.StorageCentreModel

class StorageCentreDto {

    private Long centreId
    private String centreName
    private LocationDto location
    private Double latitude
    private Double longitude
    private UserDto createdBy
    private Date createdOn
    private List<StorageInventoryDto> inventoryDtoList

    StorageCentreDto(StorageCentreModel storageCentreModel) {
        if(storageCentreModel){
            centreId = storageCentreModel.centreId
            centreName = storageCentreModel.centreName
            location = LocationsCache.getLocationById(storageCentreModel.locationId)
            latitude = storageCentreModel.latitude
            longitude = storageCentreModel.longitude
            createdBy = new UserDto(storageCentreModel.usersByCreatedBy)
            createdOn = storageCentreModel.createdOn
            inventoryDtoList = storageCentreModel.storagesByCentreId.collect{new StorageInventoryDto(it)}
        }
    }

    Long getCentreId() {
        return centreId
    }

    void setCentreId(Long centreId) {
        this.centreId = centreId
    }

    String getCentreName() {
        return centreName
    }

    void setCentreName(String centreName) {
        this.centreName = centreName
    }

    LocationDto getLocation() {
        return location
    }

    void setLocation(LocationDto location) {
        this.location = location
    }

    Double getLatitude() {
        return latitude
    }

    void setLatitude(Double latitude) {
        this.latitude = latitude
    }

    Double getLongitude() {
        return longitude
    }

    void setLongitude(Double longitude) {
        this.longitude = longitude
    }

    UserDto getCreatedBy() {
        return createdBy
    }

    void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy
    }

    Date getCreatedOn() {
        return createdOn
    }

    void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn
    }
}
