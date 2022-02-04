package com.demo.javaApplication.Service;

public interface EmailService {
    boolean sendEmailVerificationCode(String email, String emailVerificationCode, String url);
}
