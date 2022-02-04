package com.demo.javaApplication.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

    @Autowired
    private Environment env;

    public String getTokenSecret(){
        return env.getProperty("tokenSecret");
    }
    public String getEmailVerificationSenderEmail(){
        return env.getProperty("emailVerificationSenderEmail");
    }
    public String getEmailVerificationSenderPassword(){
        return env.getProperty("emailVerificationSenderPassword");
    }
}
