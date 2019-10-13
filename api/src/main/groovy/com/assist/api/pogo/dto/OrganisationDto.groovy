package com.assist.api.pogo.dto

import com.assist.api.cache.LocationsCache
import com.assist.api.models.OrganisationModel

class OrganisationDto {

    private Long organisationId
    private String name
    private String url
    private String locationName
    private String description

    OrganisationDto(OrganisationModel organisationModel) {
        if(organisationModel){
            organisationId = organisationModel.organisationId
            name = organisationModel.name
            url = organisationModel.url
            locationName = LocationsCache.getLocationById(organisationModel.locationId).locationName
            description = organisationModel.description
        }
    }

    Long getOrganisationId() {
        return organisationId
    }

    void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
    }

    String getLocationName() {
        return locationName
    }

    void setLocationName(String locationName) {
        this.locationName = locationName
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }
}
