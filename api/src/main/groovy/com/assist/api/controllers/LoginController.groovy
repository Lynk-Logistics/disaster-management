package com.assist.api.controllers

import com.assist.api.models.UserModel
import com.assist.api.pogo.response.GenericResponse
import com.assist.api.pogo.request.LoginRequest
import com.assist.api.pogo.dto.UserDto
import com.assist.api.pogo.request.UserRegistrationRequest
import com.assist.api.services.TokenProvider
import com.assist.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/auth')
class LoginController {

    @Autowired
    private UserService userService
    @Autowired
    private TokenProvider tokenProvider

    @GetMapping('/generate/{userId}')
    Map generateToken(@PathVariable('userId') Long userId){
       [success:true,token:tokenProvider.createToken(userId)]
    }

    @PostMapping('/register')
    GenericResponse registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        GenericResponse genericResponse = new GenericResponse()
        UserModel existingUserModel = userService.findUserByEmailOrMobile(userRegistrationRequest.email,userRegistrationRequest.mobile)
        if(existingUserModel){
            genericResponse.message='User already exists'
        }else{
            UserModel newUser = new UserModel(name:userRegistrationRequest.name,
                                           mobile:userRegistrationRequest.mobile,
                                           email:userRegistrationRequest.email,
                                           password:userRegistrationRequest.password
            )
            newUser = userService.save(newUser)
            if(newUser){
                genericResponse.message=tokenProvider.createToken(newUser.userId)
                genericResponse.status=true
                genericResponse.responseObject = new UserDto(newUser)
            }else{
                genericResponse.message = 'Unable to save user'
            }
        }
        genericResponse
    }

    @PostMapping('/login')
    GenericResponse loginUser(@RequestBody LoginRequest loginRequest){
        GenericResponse genericResponse = new GenericResponse()
        UserModel userModel = userService.authenticateUser(loginRequest.userIdentifier,loginRequest.password)
        if(userModel){
            genericResponse.status = true
            genericResponse.responseObject = new UserDto(userModel)
            genericResponse.message = tokenProvider.createToken(userModel.userId)
        }else{
            genericResponse.message = 'No user found'
        }
        return genericResponse
    }

}
