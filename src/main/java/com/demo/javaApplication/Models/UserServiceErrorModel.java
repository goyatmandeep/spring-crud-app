package com.demo.javaApplication.Models;

public class UserServiceErrorModel {
    private String message;
    private String timestamp;

    public UserServiceErrorModel(String timestamp, String message){
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
