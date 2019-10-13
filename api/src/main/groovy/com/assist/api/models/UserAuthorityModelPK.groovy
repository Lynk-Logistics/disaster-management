package com.assist.api.models

import javax.persistence.Column
import javax.persistence.Id
import java.io.Serializable

class UserAuthorityModelPK implements Serializable {
    private Long userId
    private Long authorityId

    @Column(name = "user_id", nullable = false)
    @Id
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Column(name = "authority_id", nullable = false)
    @Id
    Long getAuthorityId() {
        return authorityId
    }

    void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        UserAuthorityModelPK that = (UserAuthorityModelPK) o

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false
        if (authorityId != null ? !authorityId.equals(that.authorityId) : that.authorityId != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = userId != null ? userId.hashCode() : 0
        result = 31 * result + (authorityId != null ? authorityId.hashCode() : 0)
        return result
    }
}
