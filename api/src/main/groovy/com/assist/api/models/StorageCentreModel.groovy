package com.assist.api.models

import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity
@Table(name = "storage_centre", schema = "assist")
class StorageCentreModel {
    private Long centreId
    private String centreName
    private Long locationId
    private Double latitude
    private Double longitude
    private Long createdBy
    private Date createdOn
    private Collection<StorageItemModel> storagesByCentreId
    private LocationsModel locationsByLocationId
    private UserModel usersByCreatedBy

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "centre_id", nullable = false)
    Long getCentreId() {
        return centreId
    }

    void setCentreId(Long centreId) {
        this.centreId = centreId
    }

    @Basic
    @Column(name = "centre_name", nullable = false, length = 512)
    String getCentreName() {
        return centreName
    }

    void setCentreName(String centreName) {
        this.centreName = centreName
    }

    @Basic
    @Column(name = "location_id", nullable = false)
    Long getLocationId() {
        return locationId
    }

    void setLocationId(Long locationId) {
        this.locationId = locationId
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

    @Basic
    @Column(name = "created_by", nullable = false)
    Long getCreatedBy() {
        return createdBy
    }

    void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy
    }

    @Basic
    @UpdateTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = true)
    Date getCreatedOn() {
        return createdOn
    }

    void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        StorageCentreModel that = (StorageCentreModel) o

        if (centreId != null ? !centreId.equals(that.centreId) : that.centreId != null) return false
        if (centreName != null ? !centreName.equals(that.centreName) : that.centreName != null) return false
        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = centreId != null ? centreId.hashCode() : 0
        result = 31 * result + (centreName != null ? centreName.hashCode() : 0)
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0)
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0)
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0)
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0)
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0)
        return result
    }

    @OneToMany(mappedBy = "storageCentreByCentreId")
    Collection<StorageItemModel> getStoragesByCentreId() {
        return storagesByCentreId
    }

    void setStoragesByCentreId(Collection<StorageItemModel> storagesByCentreId) {
        this.storagesByCentreId = storagesByCentreId
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id", nullable = false,updatable = false,insertable = false)
    LocationsModel getLocationsByLocationId() {
        return locationsByLocationId
    }

    void setLocationsByLocationId(LocationsModel locationsByLocationId) {
        this.locationsByLocationId = locationsByLocationId
    }

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false,updatable = false,insertable = false)
    UserModel getUsersByCreatedBy() {
        return usersByCreatedBy
    }

    void setUsersByCreatedBy(UserModel usersByCreatedBy) {
        this.usersByCreatedBy = usersByCreatedBy
    }
}
