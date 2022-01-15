package com.demo.javaApplication.Exceptions;

import com.demo.javaApplication.Models.UserServiceErrorModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;


@ControllerAdvice
public class AppExceptionHandler{

    @ExceptionHandler(value={UserServiceException.class})
    public ResponseEntity<Object> handleUserServiceError(UserServiceException error, WebRequest request){
        UserServiceErrorModel errorResponse = new UserServiceErrorModel(new Date().toString(), error.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value={Exception.class})
    public ResponseEntity<Object> handleOtherError(Exception error, WebRequest request){
        UserServiceErrorModel errorResponse = new UserServiceErrorModel(new Date().toString(), error.getMessage());
        return new ResponseEntity<>(errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
