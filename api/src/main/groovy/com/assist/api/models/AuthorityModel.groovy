package com.assist.api.models

import org.springframework.security.core.GrantedAuthority

import javax.persistence.*

@Entity
@Table(name = "authority", schema = "assist")
class AuthorityModel implements GrantedAuthority{
    private Long authorityId
    private String authority
    private Collection<UserAuthorityModel> userAuthoritiesByAuthorityId

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "authority_id", nullable = false)
    Long getAuthorityId() {
        return authorityId
    }


    void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId
    }

    @Override
    @Basic
    @Column(name = "authority_name", nullable = false, length = 45)
    String getAuthority() {
        return authority
    }

    void setAuthority(String authority) {
        this.authority = authority
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        AuthorityModel that = (AuthorityModel) o

        if (authorityId != null ? !authorityId.equals(that.authorityId) : that.authorityId != null) return false
        if (authority != null ? !authority.equals(that.authority) : that.authority != null)
            return false

        return true
    }

    @Override
    int hashCode() {
        int result = authorityId != null ? authorityId.hashCode() : 0
        result = 31 * result + (authority != null ? authority.hashCode() : 0)
        return result
    }

    @OneToMany(mappedBy = "authorityByAuthorityId")
    Collection<UserAuthorityModel> getUserAuthoritiesByAuthorityId() {
        return userAuthoritiesByAuthorityId
    }

    void setUserAuthoritiesByAuthorityId(Collection<UserAuthorityModel> userAuthoritiesByAuthorityId) {
        this.userAuthoritiesByAuthorityId = userAuthoritiesByAuthorityId
    }

}
