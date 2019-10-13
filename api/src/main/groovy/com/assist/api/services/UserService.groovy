package com.assist.api.services

import com.assist.api.models.UserModel
import com.assist.api.repositories.UserRepository
import com.assist.api.util.Utils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    private UserRepository userRepository
    @Autowired
    private PasswordEncoder passwordEncoder

    UserModel findUser(String identifier){
        if(Utils.isMobile(identifier)){
            return userRepository.findByMobile(identifier).orElse(null)
        }else if(Utils.isEmail(identifier)){
            return userRepository.findByEmail(identifier).orElse(null)
        }else{
            return null
        }
    }

    UserModel authenticateUser(String identifier,String password){
        UserModel userModel = findUser(identifier)
        passwordEncoder.matches(password,userModel.password) ? userModel : null
    }

    UserModel findUserByEmailOrMobile(String email,String mobile){
        UserModel userModel = userRepository.findByMobile(mobile).orElse(null)
        if(userModel){
            return userModel
        }else{
            return userRepository.findByEmail(email).orElse(null)
        }
    }

    UserModel findById(Long id){
        return userRepository.findById(id).orElse(null)
    }

    UserModel save(UserModel userModel){
        userModel.password = passwordEncoder.encode(userModel.password)
        userRepository.save(userModel)
    }
}
