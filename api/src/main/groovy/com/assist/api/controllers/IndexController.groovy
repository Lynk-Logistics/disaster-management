package com.assist.api.controllers

import com.assist.api.security.UserPrincipal
import com.assist.api.pogo.dto.UserDto
import com.assist.api.security.CurrentUser
import com.assist.api.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/')
class IndexController {

    @Autowired
    private UserService userService

    @GetMapping('/ping')
    Map ping(@CurrentUser UserPrincipal userPrincipal){
        [success:true,date:new Date(),user: new UserDto(userService.findById(userPrincipal.userId))]
    }
}
