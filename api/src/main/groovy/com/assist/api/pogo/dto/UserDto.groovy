package com.assist.api.pogo.dto

import com.assist.api.models.UserModel

class UserDto {

    private Long userId
    private String name
    private String mobile
    private String email

    UserDto(UserModel userModel) {
        if(userModel){
            userId = userModel.userId
            name = userModel.name
            mobile = userModel.mobile
            email = userModel.email
        }
    }

    Long getUserId() {
        return userId
    }

    void setUserId(Long userId) {
        this.userId = userId
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getMobile() {
        return mobile
    }

    void setMobile(String mobile) {
        this.mobile = mobile
    }

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }
}
