package com.assist.api.pogo.dto

import com.assist.api.cache.LocationsCache
import com.assist.api.models.ActionsModel

class ActionDto {

    private Long actionId
    private String actionType
    private String description
    private String itemName
    private Long itemQuantity
    private Boolean fulfilled
    private Double latitude
    private Double longitude
    private UserDto postedByUser
    private OrganisationDto postedByOrganisation
    private LocationDto location
    private FilesDto file

    ActionDto(ActionsModel actionsModel){
        if(actionsModel){
            actionId = actionsModel.actionId
            actionType = actionsModel.actionType
            description = actionsModel.description
            itemName = actionsModel.itemName
            itemQuantity = actionsModel.itemQuantity
            fulfilled = actionsModel.fulfilled
            latitude = actionsModel.latitude
            longitude = actionsModel.longitude
            postedByUser = actionsModel.userByPostedByUser ? new UserDto(actionsModel.userByPostedByUser) : null
            postedByOrganisation = actionsModel.organisationByPostedByOrganisation ? new OrganisationDto(actionsModel.organisationByPostedByOrganisation) : null
            location = actionsModel.locationId ? LocationsCache.getLocationById(actionsModel.locationId) : null
            file = actionsModel.filesByFileId ? new FilesDto(actionsModel.filesByFileId) : null
        }
    }

    Long getActionId() {
        return actionId
    }

    void setActionId(Long actionId) {
        this.actionId = actionId
    }

    String getActionType() {
        return actionType
    }

    void setActionType(String actionType) {
        this.actionType = actionType
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    String getItemName() {
        return itemName
    }

    void setItemName(String itemName) {
        this.itemName = itemName
    }

    Long getItemQuantity() {
        return itemQuantity
    }

    void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity
    }

    Boolean getFulfilled() {
        return fulfilled
    }

    void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled
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

    UserDto getPostedByUser() {
        return postedByUser
    }

    void setPostedByUser(UserDto postedByUser) {
        this.postedByUser = postedByUser
    }

    OrganisationDto getPostedByOrganisation() {
        return postedByOrganisation
    }

    void setPostedByOrganisation(OrganisationDto postedByOrganisation) {
        this.postedByOrganisation = postedByOrganisation
    }

    LocationDto getLocation() {
        return location
    }

    void setLocation(LocationDto location) {
        this.location = location
    }

    FilesDto getFile() {
        return file
    }

    void setFile(FilesDto file) {
        this.file = file
    }
}
