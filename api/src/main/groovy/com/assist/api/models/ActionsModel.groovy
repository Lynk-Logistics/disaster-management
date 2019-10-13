package com.assist.api.models

import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity
@Table(name = "actions", schema = "assist")
class ActionsModel {
    private Long actionId
    private String actionType
    private String description
    private String itemName
    private Long itemQuantity
    private Long postedByUser
    private Long postedByOrganisation
    private Boolean fulfilled
    private Long locationId
    private Double latitude
    private Double longitude
    private Long fileId
    private Date createdOn
    private UserModel userByPostedByUser
    private OrganisationModel organisationByPostedByOrganisation
    private LocationsModel locationsByLocationId
    private FilesModel filesByFileId

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "action_id", nullable = false)
    Long getActionId() {
        return actionId
    }

    void setActionId(Long actionId) {
        this.actionId = actionId
    }

    @Basic
    @Column(name = "action_type", nullable = false, length = 100)
    String getActionType() {
        return actionType
    }

    void setActionType(String actionType) {
        this.actionType = actionType
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    @Basic
    @Column(name = "item_name", nullable = true, length = 256)
    String getItemName() {
        return itemName
    }

    void setItemName(String itemName) {
        this.itemName = itemName
    }

    @Basic
    @Column(name = "item_quantity", nullable = true)
    Long getItemQuantity() {
        return itemQuantity
    }

    void setItemQuantity(Long itemQuantity) {
        this.itemQuantity = itemQuantity
    }

    @Basic
    @Column(name = "posted_by_user", nullable = true)
    Long getPostedByUser() {
        return postedByUser
    }

    void setPostedByUser(Long postedByUser) {
        this.postedByUser = postedByUser
    }

    @Basic
    @Column(name = "posted_by_organisation", nullable = true,insertable = false,updatable = false)
    Long getPostedByOrganisation() {
        return postedByOrganisation
    }

    void setPostedByOrganisation(Long postedByOrganisation) {
        this.postedByOrganisation = postedByOrganisation
    }

    @Basic
    @Column(name = "fulfilled", nullable = false)
    Boolean getFulfilled() {
        return fulfilled
    }

    void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled
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
    @Column(name = "file_id", nullable = true,insertable = false,updatable = false)
    Long getFileId() {
        return fileId
    }

    void setFileId(Long fileId) {
        this.fileId = fileId
    }

    @Basic
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
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

        ActionsModel that = (ActionsModel) o

        if (actionId != null ? !actionId.equals(that.actionId) : that.actionId != null) return false
        if (actionType != null ? !actionType.equals(that.actionType) : that.actionType != null) return false
        if (description != null ? !description.equals(that.description) : that.description != null) return false
        if (itemName != null ? !itemName.equals(that.itemName) : that.itemName != null) return false
        if (itemQuantity != null ? !itemQuantity.equals(that.itemQuantity) : that.itemQuantity != null) return false
        if (postedByUser != null ? !postedByUser.equals(that.postedByUser) : that.postedByUser != null) return false
        if (postedByOrganisation != null ? !postedByOrganisation.equals(that.postedByOrganisation) : that.postedByOrganisation != null)
            return false
        if (fulfilled != null ? !fulfilled.equals(that.fulfilled) : that.fulfilled != null) return false
        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false
        if (latitude != null ? !latitude.equals(that.latitude) : that.latitude != null) return false
        if (longitude != null ? !longitude.equals(that.longitude) : that.longitude != null) return false
        if (fileId != null ? !fileId.equals(that.fileId) : that.fileId != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = actionId != null ? actionId.hashCode() : 0
        result = 31 * result + (actionType != null ? actionType.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        result = 31 * result + (itemName != null ? itemName.hashCode() : 0)
        result = 31 * result + (itemQuantity != null ? itemQuantity.hashCode() : 0)
        result = 31 * result + (postedByUser != null ? postedByUser.hashCode() : 0)
        result = 31 * result + (postedByOrganisation != null ? postedByOrganisation.hashCode() : 0)
        result = 31 * result + (fulfilled != null ? fulfilled.hashCode() : 0)
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0)
        result = 31 * result + (latitude != null ? latitude.hashCode() : 0)
        result = 31 * result + (longitude != null ? longitude.hashCode() : 0)
        result = 31 * result + (fileId != null ? fileId.hashCode() : 0)
        return result
    }

    @ManyToOne
    @JoinColumn(name = "posted_by_user", referencedColumnName = "user_id",insertable = false,updatable = false)
    UserModel getUserByPostedByUser() {
        return userByPostedByUser
    }

    void setUserByPostedByUser(UserModel userByPostedByUser) {
        this.userByPostedByUser = userByPostedByUser
    }

    @ManyToOne
    @JoinColumn(name = "posted_by_organisation", referencedColumnName = "organisation_id",insertable = false,updatable = false)
    OrganisationModel getOrganisationByPostedByOrganisation() {
        return organisationByPostedByOrganisation
    }

    void setOrganisationByPostedByOrganisation(OrganisationModel organisationByPostedByOrganisation) {
        this.organisationByPostedByOrganisation = organisationByPostedByOrganisation
    }

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id",insertable = false,updatable = false)
    LocationsModel getLocationsByLocationId() {
        return locationsByLocationId
    }

    void setLocationsByLocationId(LocationsModel locationsByLocationId) {
        this.locationsByLocationId = locationsByLocationId
    }

    @ManyToOne
    @JoinColumn(name = "file_id", referencedColumnName = "file_id",insertable = false,updatable = false)
    FilesModel getFilesByFileId() {
        return filesByFileId
    }

    void setFilesByFileId(FilesModel filesByFileId) {
        this.filesByFileId = filesByFileId
    }
}
