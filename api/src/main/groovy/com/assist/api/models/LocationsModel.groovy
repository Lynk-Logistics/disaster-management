package com.assist.api.models

import javax.persistence.*
import java.util.Collection

@Entity
@Table(name = "locations", schema = "assist")
class LocationsModel {
    private Long locationId
    private String locationName
    private Double latitude
    private Double longitude
    private Collection<ActionsModel> actionsByLocationId
    private Collection<OrganisationModel> organisationsByLocationId
    private Collection<StorageCentreModel> storageCentresByLocationId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id", nullable = false)
    Long getLocationId() {
        return locationId
    }

    void setLocationId(Long locationId) {
        this.locationId = locationId
    }

    @Basic
    @Column(name = "location_name", nullable = false, length = 256)
    String getLocationName() {
        return locationName
    }

    void setLocationName(String locationName) {
        this.locationName = locationName
    }

    @Basic
    @Column(name = "latitude", nullable = true, precision = 0)
    Double getLatitude() {
        return latitude
    }

    void setLatitude(Double latitude) {
        this.latitude = latitude
    }

    @Basic
    @Column(name = "longitude", nullable = true, precision = 0)
    Double getLongitude() {
        return longitude
    }

    void setLongitude(Double longitude) {
        this.longitude = longitude
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        LocationsModel that = (LocationsModel) o

        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false
        if (locationName != null ? !locationName.equals(that.locationName) : that.locationName != null) return false
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = locationId != null ? locationId.hashCode() : 0
        result = 31 * result + (locationName != null ? locationName.hashCode() : 0)
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0)
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0)
        return result
    }

    @OneToMany(mappedBy = "locationsByLocationId")
    Collection<ActionsModel> getActionsByLocationId() {
        return actionsByLocationId
    }

    void setActionsByLocationId(Collection<ActionsModel> actionsByLocationId) {
        this.actionsByLocationId = actionsByLocationId
    }

    @OneToMany(mappedBy = "locationsByLocationId")
    Collection<OrganisationModel> getOrganisationsByLocationId() {
        return organisationsByLocationId
    }

    void setOrganisationsByLocationId(Collection<OrganisationModel> organisationsByLocationId) {
        this.organisationsByLocationId = organisationsByLocationId
    }

    @OneToMany(mappedBy = "locationsByLocationId")
    Collection<StorageCentreModel> getStorageCentresByLocationId() {
        return storageCentresByLocationId;
    }

    void setStorageCentresByLocationId(Collection<StorageCentreModel> storageCentresByLocationId) {
        this.storageCentresByLocationId = storageCentresByLocationId;
    }
}
