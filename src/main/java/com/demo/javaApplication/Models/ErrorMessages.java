package com.demo.javaApplication.Models;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required fields, please check documentation for required fields"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found");

    private final String errorMessage;

    ErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }


}
