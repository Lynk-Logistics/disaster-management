package com.assist.api.pogo.request

class UserRegistrationRequest {

    private String email
    private String mobile
    private String password
    private String name

    String getEmail() {
        return email
    }

    void setEmail(String email) {
        this.email = email
    }

    String getMobile() {
        return mobile
    }

    void setMobile(String mobile) {
        this.mobile = mobile
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }
}
