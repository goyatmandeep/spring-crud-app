package com.demo.javaApplication.Shared;

public enum ErrorMessages {
    MISSING_REQUIRED_FIELD("Missing required fields, please check documentation for required fields"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    NO_RECORD_FOUND("No record found"),
    TOKEN_EXPIRED("Token is expired.");

    private final String errorMessage;

    ErrorMessages(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage(){
        return this.errorMessage;
    }


}
