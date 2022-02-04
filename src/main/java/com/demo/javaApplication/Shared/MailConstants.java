package com.demo.javaApplication.Shared;

import com.demo.javaApplication.Security.AppProperties;
import com.demo.javaApplication.SpringApplicationContext;

public class MailConstants {
    public static final String HOST = "smtp.gmail.com";
    public static String getEmailVerificationSenderEmail(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getEmailVerificationSenderEmail();
    }

    public static String getEmailVerificationSenderPassword(){
        AppProperties appProperties = (AppProperties) SpringApplicationContext.getBean("AppProperties");
        return appProperties.getEmailVerificationSenderPassword();
    }

}
