package com.demo.javaApplication.Exceptions;

public class UserServiceException extends RuntimeException{

    public UserServiceException(String message){
        super(message);
    }

}
