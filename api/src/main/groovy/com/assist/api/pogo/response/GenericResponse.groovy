package com.assist.api.pogo.response

class GenericResponse {

    private String message
    private boolean status
    private Object responseObject

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }

    boolean getStatus() {
        return status
    }

    void setStatus(boolean status) {
        this.status = status
    }

    Object getResponseObject() {
        return responseObject
    }

    void setResponseObject(Object responseObject) {
        this.responseObject = responseObject
    }
}
