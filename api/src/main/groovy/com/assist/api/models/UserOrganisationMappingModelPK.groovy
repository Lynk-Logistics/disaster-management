package com.assist.api.models

import javax.persistence.Column
import javax.persistence.Id
import java.io.Serializable

class UserOrganisationMappingModelPK implements Serializable {
    private Long organisationId
    private Long userId

    @Column(name = "organisation_id", nullable = false)
    @Id
    Long getOrganisationId() {
        return organisationId
    }

    void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId
    }

    @Column(name = "user_id", nullable = false)
    @Id
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        UserOrganisationMappingModelPK that = (UserOrganisationMappingModelPK) o

        if (organisationId != null ? !organisationId.equals(that.organisationId) : that.organisationId != null)
            return false
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = organisationId != null ? organisationId.hashCode() : 0
        result = 31 * result + (userId != null ? userId.hashCode() : 0)
        return result
    }
}
