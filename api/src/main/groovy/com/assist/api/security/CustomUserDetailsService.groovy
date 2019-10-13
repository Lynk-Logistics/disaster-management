package com.assist.api.security

import com.assist.api.models.UserModel
import com.assist.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserService userService

    @Override
    UserDetails loadUserByUsername(String userIdentifier) throws UsernameNotFoundException {
        UserModel userModel = userService.findUser(userIdentifier)
        if(userModel){
            return new UserPrincipal(userModel)
        }
        return null
    }

    UserDetails loadUserById(Long id) {
        UserModel userModel = userService.findById(id)
        if(userModel){
            return new UserPrincipal(userModel)
        }
        return null
    }
}
