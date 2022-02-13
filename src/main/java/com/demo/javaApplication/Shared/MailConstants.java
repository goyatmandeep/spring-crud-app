package com.demo.javaApplication.Shared;

import com.demo.javaApplication.Security.AppProperties;
import com.demo.javaApplication.SpringApplicationContext;

public class MailConstants {
    public static final String HOST = "smtp.gmail.com";
    public static final String EMAIL_VERIFICATION_MESSAGE = "Hey, verify your email. ";
    public static final String PASSWORD_RESET_MESSAGE = "Reset your password here. ";
    public static final String MAIL_SUBJECT = "Message from blogs.com";
    public static final String PATH_EMAIL_VERIFICATION = "./src/main/resources/static/verify_email_email.html";
    public static final String PATH_FORGOT_PASSWORD = "./src/main/resources/static/forgot_password_email.html";
    public static String getEmailVerificationSenderEmail(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getEmailVerificationSenderEmail();
    }

    public static String getEmailVerificationSenderPassword(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getEmailVerificationSenderPassword();
    }

}
