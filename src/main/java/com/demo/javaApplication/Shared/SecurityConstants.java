package com.demo.javaApplication.Shared;

import com.demo.javaApplication.Security.AppProperties;
import com.demo.javaApplication.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 604800000;
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String HEADER_STRING_USERID = "UserID";

    public static String getTokenSecret(){
       AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
       return appProperties.getTokenSecret();
    }



}
