package com.assist.api.models

import javax.persistence.*
import java.util.Collection

@Entity
@Table(name = "users", schema = "assist")
class UserModel {
    private Long userId
    private String name
    private String mobile
    private String email
    private String password
    private Collection<ActionsModel> actionsByUserId
    private Collection<OrganisationModel> organisationsByUserId
    private Collection<StorageCentreModel> storageCentresByUserId
    private Collection<UserAuthorityModel> userAuthoritiesByUserId
    private Collection<UserOrganisationMappingModel> userOrganisationMappingsByUserId
    private Collection<UserOrganisationMappingModel> userOrganisationMappingsByUserId_0

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 256)
    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    @Basic
    @Column(name = "mobile", nullable = true,length = 15)
    String getMobile() {
        return mobile
    }

    void setMobile(String mobile) {
        this.mobile = mobile
    }

    @Basic
    @Column(name = "email", nullable = true, length = 246)
    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    @Basic
    @Column(name = "passwd", nullable = false, length = 256)
    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    @Override
    boolean equals(Object o) {
        if (this == o) return true
        if (o == null || getClass() != o.getClass()) return false

        UserModel userModel = (UserModel) o

        if (userId != null ? !userId.equals(userModel.userId) : userModel.userId != null) return false
        if (name != null ? !name.equals(userModel.name) : userModel.name != null) return false
        if (mobile != null ? !mobile.equals(userModel.mobile) : userModel.mobile != null) return false
        if (email != null ? !email.equals(userModel.email) : userModel.email != null) return false
        if (password != null ? !password.equals(userModel.password) : userModel.password != null) return false

        return true
    }

    @Override
    int hashCode() {
        int result = userId != null ? userId.hashCode() : 0
        result = 31 * result + (name != null ? name.hashCode() : 0)
        result = 31 * result + (mobile != null ? mobile.hashCode() : 0)
        result = 31 * result + (email != null ? email.hashCode() : 0)
        result = 31 * result + (password != null ? password.hashCode() : 0)
        return result
    }

    @OneToMany(mappedBy = "userByPostedByUser")
    Collection<ActionsModel> getActionsByUserId() {
        return actionsByUserId
    }

    void setActionsByUserId(Collection<ActionsModel> actionsByUserId) {
        this.actionsByUserId = actionsByUserId
    }

    @OneToMany(mappedBy = "userByCreatedBy")
    Collection<OrganisationModel> getOrganisationsByUserId() {
        return organisationsByUserId
    }

    void setOrganisationsByUserId(Collection<OrganisationModel> organisationsByUserId) {
        this.organisationsByUserId = organisationsByUserId
    }

    @OneToMany(mappedBy = "usersByCreatedBy")
    Collection<StorageCentreModel> getStorageCentresByUserId() {
        return storageCentresByUserId;
    }

    void setStorageCentresByUserId(Collection<StorageCentreModel> storageCentresByUserId) {
        this.storageCentresByUserId = storageCentresByUserId;
    }

    @OneToMany(mappedBy = "userByUserId",fetch = FetchType.EAGER)
    Collection<UserAuthorityModel> getUserAuthoritiesByUserId() {
        return userAuthoritiesByUserId
    }

    void setUserAuthoritiesByUserId(Collection<UserAuthorityModel> userAuthoritiesByUserId) {
        this.userAuthoritiesByUserId = userAuthoritiesByUserId
    }

    @OneToMany(mappedBy = "userByUserId")
    Collection<UserOrganisationMappingModel> getUserOrganisationMappingsByUserId() {
        return userOrganisationMappingsByUserId
    }

    void setUserOrganisationMappingsByUserId(Collection<UserOrganisationMappingModel> userOrganisationMappingsByUserId) {
        this.userOrganisationMappingsByUserId = userOrganisationMappingsByUserId
    }

    @OneToMany(mappedBy = "userByAddedBy")
    Collection<UserOrganisationMappingModel> getUserOrganisationMappingsByUserId_0() {
        return userOrganisationMappingsByUserId_0
    }

    void setUserOrganisationMappingsByUserId_0(Collection<UserOrganisationMappingModel> userOrganisationMappingsByUserId_0) {
        this.userOrganisationMappingsByUserId_0 = userOrganisationMappingsByUserId_0
    }
}
