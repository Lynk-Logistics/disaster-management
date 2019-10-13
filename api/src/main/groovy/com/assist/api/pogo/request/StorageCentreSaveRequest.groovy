package com.assist.api.pogo.request

class StorageCentreSaveRequest {

    private Long centreId
    private String centreName
    private Integer locationId
    private Double latitude
    private Double longitude
    private Long createdBy

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

    Integer getLocationId() {
        return locationId
    }

    void setLocationId(Integer locationId) {
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

    Long getCreatedBy() {
        return createdBy
    }

    void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy
    }
}
