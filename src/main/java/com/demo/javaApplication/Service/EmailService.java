package com.demo.javaApplication.Service;

import java.io.IOException;

public interface EmailService {
    boolean sendEmail(String email, String filePath, String token);
    String readEmail(String path) throws IOException;
}
