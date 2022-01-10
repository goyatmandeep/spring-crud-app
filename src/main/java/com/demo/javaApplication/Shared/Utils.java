package com.demo.javaApplication.Shared;

import com.demo.javaApplication.Service.UserServiceImpl;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Utils {
    private final String Alphabet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final Random random = new SecureRandom();
    private static final Logger LOGGER = Logger.getLogger(Utils.class.getName());

    public String generateUserID(int length){

        StringBuilder stringBuilder = new StringBuilder(length);

        for(int i=0; i<length; i++){
            stringBuilder.append(Alphabet.charAt(random.nextInt(Alphabet.length())));
        }
        LOGGER.log(Level.INFO, "New user ID generated: "+stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String generateAddressID(int length){

        StringBuilder stringBuilder = new StringBuilder(length);

        for(int i=0; i<length; i++){
            stringBuilder.append(Alphabet.charAt(random.nextInt(Alphabet.length())));
        }
        LOGGER.log(Level.INFO, "New user ID generated: "+stringBuilder.toString());
        return stringBuilder.toString();
    }

}
