package com.assist.api.pogo.request

class ActionSaveRequest {

    private Long actionId
    private String actionType
    private String description
    private String itemName
    private Long itemQuantity
    private Long postedByUser
    private Long postedByOrganisation
    private boolean fulfilled
    private Long locationId
    private Double latitude
    private Double longitude
    private Long fileId

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

    Long getPostedByUser() {
        return postedByUser
    }

    void setPostedByUser(Long postedByUser) {
        this.postedByUser = postedByUser
    }

    Long getPostedByOrganisation() {
        return postedByOrganisation
    }

    void setPostedByOrganisation(Long postedByOrganisation) {
        this.postedByOrganisation = postedByOrganisation
    }

    boolean getFulfilled() {
        return fulfilled
    }

    void setFulfilled(boolean fulfilled) {
        this.fulfilled = fulfilled
    }

    Long getLocationId() {
        return locationId
    }

    void setLocationId(Long locationId) {
        this.locationId = locationId
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

    Long getFileId() {
        return fileId
    }

    void setFileId(Long fileId) {
        this.fileId = fileId
    }
}
