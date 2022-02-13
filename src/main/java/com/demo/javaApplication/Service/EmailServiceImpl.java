package com.demo.javaApplication.Service;

import com.demo.javaApplication.Shared.MailConstants;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailServiceImpl implements EmailService {
    @Override
    public boolean sendEmail(String emailTo, String filePath, String token){
        Properties prop = new Properties();
        prop.put("mail.smtp.host", MailConstants.HOST);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.ssl.trust", MailConstants.HOST);
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2");
//        prop.put("mail.debug", "true");

        Session session = Session.getDefaultInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MailConstants.getEmailVerificationSenderEmail(),MailConstants.getEmailVerificationSenderPassword());
                    }
                });

        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailTo));
            message.setSubject(MailConstants.MAIL_SUBJECT);
            String htmlEmail = readEmail(filePath);
            String newHtmlEmail = htmlEmail.replace("$token", token);
            message.setContent(newHtmlEmail, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException | IOException e) {throw new RuntimeException(e);}

        return true;
    }

    @Override
    public String readEmail(String path) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String str;

        BufferedReader in = new BufferedReader(new FileReader(path));
        while((str = in.readLine())!=null)
            stringBuilder.append(str);
        in.close();
        return  stringBuilder.toString();
    }
}

        /*
        String senderEmail = MailConstants.getEmailVerificationSenderEmail();
        String host = "mail.smtp.host";
        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put(host, MailConstants.HOST);
        properties.put("mail.smtp.ssl.trust",MailConstants.HOST);
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.debug", "true");

//        properties.put("mail.smtp.port", "465");
//        properties.put("mail.smtp.ssl.enable", "true");
//        properties.put("mail.smtp.auth", "true");
//        properties.setProperty(host, MailConstants.HOST);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);


        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(senderEmail));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(senderEmail));

            // Set Subject: header field
            message.setSubject("Verify Your Email");

            // Now set the actual message
            message.setText("Verify your email id - "+url+emailVerificationCode);
            Transport transport = session.getTransport("smtp");
            transport.connect (MailConstants.HOST, 587, senderEmail, MailConstants.getEmailVerificationSenderPassword());
            // Send message
            transport.send(message, message.getAllRecipients());
        } catch (MessagingException mex) {
            mex.printStackTrace();
            throw new RuntimeException("Email delivery failed");
        }
       return true;
    }
        */


