package com.demo.javaApplication.Shared;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Date;
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

    public boolean isTokenExpired(String token){
        Claims claims = Jwts.parser()
                            .setSigningKey(SecurityConstants.getTokenSecret())
                            .parseClaimsJws(token).getBody();
        Date expirationDate = claims.getExpiration();
        return expirationDate.before(new Date());
    }

    public String generateEmailVerificationToken(String userID){
        return Jwts.builder()
            .setSubject(userID)
            .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EMAIL_VERIFICATION_CODE_EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret())
            .compact();
    }

}
