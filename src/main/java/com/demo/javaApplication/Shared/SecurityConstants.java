package com.demo.javaApplication.Shared;

import com.demo.javaApplication.Security.AppProperties;
import com.demo.javaApplication.SpringApplicationContext;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 604800000;
    public static final long EMAIL_VERIFICATION_CODE_EXPIRATION_TIME = 604800000;
    public static final long PASSWORD_RESET_CODE_EXPIRATION_TIME = 3600000; //1 hour
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String LOGIN_URL = "/users/login";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";  //hardcoded in rest controller get method
    public static final String HEADER_STRING_USERID = "UserID";
    public static final String FORGOT_PASSWORD_URL = "/users/forgot-password";
    public static final String RESET_PASSWORD_URL = "/users/reset-password";
    public static final String VERIFY_EMAIL_HTML_URL = "/verify_email.html";
    public static final String RESET_PASSWORD_HTML_URL = "/forgot_password.html";
    public static String getTokenSecret(){
       AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
       return appProperties.getTokenSecret();
    }



}
