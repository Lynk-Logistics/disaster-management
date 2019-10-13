package com.assist.api.pogo.dto

import com.assist.api.models.LocationsModel

class LocationDto {

    private Long locationId
    private String locationName
    private Double latitude
    private Double longitude

    LocationDto(LocationsModel locationsModel) {
        if(locationsModel){
            locationId = locationsModel.locationId
            locationName = locationsModel.locationName
            latitude = locationsModel.latitude
            longitude = locationsModel.longitude
        }

    }

    Long getLocationId() {
        return locationId
    }

    void setLocationId(Long locationId) {
        this.locationId = locationId
    }

    String getLocationName() {
        return locationName
    }

    void setLocationName(String locationName) {
        this.locationName = locationName
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
}
