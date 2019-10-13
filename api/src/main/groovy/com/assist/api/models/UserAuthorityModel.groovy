package com.assist.api.models

import javax.persistence.*

@Entity
@Table(name = "user_authority", schema = "assist")
@IdClass(UserAuthorityModelPK.class)
class UserAuthorityModel {
    private Long userId
    private Long authorityId
    private UserModel userByUserId
    private AuthorityModel authorityByAuthorityId

    @Id
    @Column(name = "user_id", nullable = false)
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Id
    @Column(name = "authority_id", nullable = false)
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

        UserAuthorityModel that = (UserAuthorityModel) o

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

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    UserModel getUserByUserId() {
        return userByUserId
    }

    void setUserByUserId(UserModel userByUserId) {
        this.userByUserId = userByUserId
    }

    @ManyToOne
    @JoinColumn(name = "authority_id", referencedColumnName = "authority_id", nullable = false,insertable = false,updatable = false)
    AuthorityModel getAuthorityByAuthorityId() {
        return authorityByAuthorityId
    }

    void setAuthorityByAuthorityId(AuthorityModel authorityByAuthorityId) {
        this.authorityByAuthorityId = authorityByAuthorityId
    }
}
