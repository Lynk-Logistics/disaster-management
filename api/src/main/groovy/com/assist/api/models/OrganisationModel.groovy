package com.assist.api.models

import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity
@Table(name = "organisation", schema = "assist")
class OrganisationModel {
    private Long organisationId
    private String name
    private String url
    private Long locationId
    private Long createdBy
    private Date createdOn
    private String description
    private Collection<ActionsModel> actionsByOrganisationId
    private LocationsModel locationsByLocationId
    private UserModel userByCreatedBy
    private Collection<UserOrganisationMappingModel> userOrganisationMappingsByOrganisationId

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organisation_id", nullable = false)
    Long getOrganisationId() {
        return organisationId
    }

    void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    @Basic
    @Column(name = "url", nullable = true, length = 45)
    String getUrl() {
        return url
    }

    void setUrl(String url) {
        this.url = url
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
    @Column(name = "created_on", nullable = false)
    Date getCreatedOn() {
        return createdOn
    }

    void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn
    }

    @Basic
    @Column(name = "description", nullable = true, length = -1)
    String getDescription() {
        return description
    }

    void setDescription(String description) {
        this.description = description
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        OrganisationModel that = (OrganisationModel) o

        if (organisationId != null ? !organisationId.equals(that.organisationId) : that.organisationId != null)
            return false
        if (name != null ? !name.equals(that.name) : that.name != null) return false
        if (url != null ? !url.equals(that.url) : that.url != null) return false
        if (locationId != null ? !locationId.equals(that.locationId) : that.locationId != null) return false
        if (createdBy != null ? !createdBy.equals(that.createdBy) : that.createdBy != null) return false
        if (createdOn != null ? !createdOn.equals(that.createdOn) : that.createdOn != null) return false
        if (description != null ? !description.equals(that.description) : that.description != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = organisationId != null ? organisationId.hashCode() : 0
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (url != null ? url.hashCode() : 0)
        result = 31 * result + (locationId != null ? locationId.hashCode() : 0)
        result = 31 * result + (createdBy != null ? createdBy.hashCode() : 0)
        result = 31 * result + (createdOn != null ? createdOn.hashCode() : 0)
        result = 31 * result + (description != null ? description.hashCode() : 0)
        return result
    }

    @OneToMany(mappedBy = "organisationByPostedByOrganisation")
    Collection<ActionsModel> getActionsByOrganisationId() {
        return actionsByOrganisationId
    }

    void setActionsByOrganisationId(Collection<ActionsModel> actionsByOrganisationId) {
        this.actionsByOrganisationId = actionsByOrganisationId
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
    @JoinColumn(name = "created_by", referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    UserModel getUserByCreatedBy() {
        return userByCreatedBy
    }

    void setUserByCreatedBy(UserModel userByCreatedBy) {
        this.userByCreatedBy = userByCreatedBy
    }

    @OneToMany(mappedBy = "organisationByOrganisationId")
    Collection<UserOrganisationMappingModel> getUserOrganisationMappingsByOrganisationId() {
        return userOrganisationMappingsByOrganisationId
    }

    void setUserOrganisationMappingsByOrganisationId(Collection<UserOrganisationMappingModel> userOrganisationMappingsByOrganisationId) {
        this.userOrganisationMappingsByOrganisationId = userOrganisationMappingsByOrganisationId
    }
}
