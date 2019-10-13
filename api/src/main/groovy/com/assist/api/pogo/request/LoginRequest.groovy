package com.assist.api.pogo.request

class LoginRequest {

    private String userIdentifier
    private String password

    String getUserIdentifier() {
        return userIdentifier
    }

    void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier
    }

    String getPassword() {
        return password
    }

    void setPassword(String password) {
        this.password = password
    }
}
