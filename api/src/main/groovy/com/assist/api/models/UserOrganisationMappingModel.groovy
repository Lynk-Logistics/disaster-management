package com.assist.api.models

import org.hibernate.annotations.UpdateTimestamp

import javax.persistence.*

@Entity
@Table(name = "user_organisation_mapping", schema = "assist")
@IdClass(UserOrganisationMappingModelPK)
class UserOrganisationMappingModel {
    private Long organisationId
    private Long userId
    private boolean isAdmin
    private Date addedOn
    private Long addedBy
    private OrganisationModel organisationByOrganisationId
    private UserModel userByUserId
    private UserModel userByAddedBy

    @Id
    @Column(name = "organisation_id", nullable = false)
    Long getOrganisationId() {
        return organisationId
    }

    void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId
    }

    @Id
    @Column(name = "user_id", nullable = false)
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Basic
    @Column(name = "is_admin", nullable = false)
    boolean getIsAdmin() {
        return isAdmin
    }

    void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin
    }

    @Basic
    @UpdateTimestamp
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "added_on", nullable = false)
    Date getAddedOn() {
        return addedOn
    }

    void setAddedOn(Date addedOn) {
        this.addedOn = addedOn
    }

    @Basic
    @Column(name = "added_by", nullable = false)
    Long getAddedBy() {
        return addedBy
    }

    void setAddedBy(Long addedBy) {
        this.addedBy = addedBy
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        UserOrganisationMappingModel that = (UserOrganisationMappingModel) o

        if (organisationId != null ? !organisationId.equals(that.organisationId) : that.organisationId != null)
            return false
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false
        if (isAdmin != null ? !isAdmin.equals(that.isAdmin) : that.isAdmin != null) return false
        if (addedOn != null ? !addedOn.equals(that.addedOn) : that.addedOn != null) return false
        if (addedBy != null ? !addedBy.equals(that.addedBy) : that.addedBy != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = organisationId != null ? organisationId.hashCode() : 0
        result = 31 * result + (userId != null ? userId.hashCode() : 0)
        result = 31 * result + (isAdmin != null ? isAdmin.hashCode() : 0)
        result = 31 * result + (addedOn != null ? addedOn.hashCode() : 0)
        result = 31 * result + (addedBy != null ? addedBy.hashCode() : 0)
        return result
    }

    @ManyToOne
    @JoinColumn(name = "organisation_id", referencedColumnName = "organisation_id", nullable = false,insertable = false,updatable = false)
    OrganisationModel getOrganisationByOrganisationId() {
        return organisationByOrganisationId
    }

    void setOrganisationByOrganisationId(OrganisationModel organisationByOrganisationId) {
        this.organisationByOrganisationId = organisationByOrganisationId
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    UserModel getUserByUserId() {
        return userByUserId
    }

    void setUserByUserId(UserModel userByUserId) {
        this.userByUserId = userByUserId
    }

    @ManyToOne
    @JoinColumn(name = "added_by", referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    UserModel getUserByAddedBy() {
        return userByAddedBy
    }

    void setUserByAddedBy(UserModel userByAddedBy) {
        this.userByAddedBy = userByAddedBy
    }
}
