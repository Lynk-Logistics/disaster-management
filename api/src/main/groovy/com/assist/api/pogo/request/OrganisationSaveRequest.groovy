package com.assist.api.pogo.request

class OrganisationSaveRequest {

    private Long organisationId
    private String name
    private String url
    private Long locationId
    private String description

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

    Long getLocationId() {
        return locationId
    }

    void setLocationId(Long locationId) {
        this.locationId = locationId
    }

    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }
}
