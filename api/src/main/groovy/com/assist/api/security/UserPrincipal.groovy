package com.assist.api.security

import com.assist.api.models.UserModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserPrincipal implements UserDetails {

    private Long userId
    private String email
    private String mobile
    private String password
    private List authorities

    UserPrincipal(UserModel userModel){
        userId = userModel.userId
        email = userModel.email
        mobile = userModel.mobile
        password = userModel.password
        authorities = userModel.userAuthoritiesByUserId
    }

    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities
    }

    @Override
    String getPassword() {
        return password
    }

    @Override
    String getUsername() {
        return email ?: mobile
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}
