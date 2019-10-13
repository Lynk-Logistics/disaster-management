package com.assist.api.pogo.dto

import com.assist.api.models.OrganisationModel

class OrganisationDetailsDto {

    private Long organisationId
    private String name
    private String url
    private Date createdOn
    private String description
    private LocationDto location
    private UserDto createdBy
    private List<UserDto> mappedUsers

    OrganisationDetailsDto(OrganisationModel organisationModel) {
        if(organisationModel){
            organisationId = organisationModel.organisationId
            name = organisationModel.name
            url = organisationModel.url
            createdOn = organisationModel.createdOn
            description = organisationModel.description
            location = new LocationDto(organisationModel.locationsByLocationId)
            createdBy = new UserDto(organisationModel.userByCreatedBy)
            mappedUsers = organisationModel.userOrganisationMappingsByOrganisationId.collect {new UserDto(it.userByUserId)}
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

    Date getCreatedOn() {
        return createdOn
    }

    void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    LocationDto getLocation() {
        return location
    }

    void setLocation(LocationDto location) {
        this.location = location
    }

    UserDto getCreatedBy() {
        return createdBy
    }

    void setCreatedBy(UserDto createdBy) {
        this.createdBy = createdBy
    }

    List<UserDto> getMappedUsers() {
        return mappedUsers
    }

    void setMappedUsers(List<UserDto> mappedUsers) {
        this.mappedUsers = mappedUsers
    }
}
