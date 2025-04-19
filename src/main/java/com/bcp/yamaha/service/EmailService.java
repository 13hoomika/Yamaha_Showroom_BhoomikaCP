package com.bcp.yamaha.service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@Slf4j
public class EmailService {
    // Email configuration constants
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String USERNAME = "bhoomikacp.xworkz@gmail.com";
    private static final String PASSWORD = "zilw thew euxr dmsr";
    private static final String DEFAULT_SUBJECT = "Your Generated Password";

    /**
     * Sends an email with custom subject and message
     *
     * @param recipientEmail The email address of the recipient
     * @param subject The subject of the email
     * @param messageText The content of the email
     * @return true if email was sent successfully, false otherwise
     */
    public boolean sendEmail(String recipientEmail, String subject, String messageText) {
        // Validate inputs
        if (recipientEmail == null || recipientEmail.trim().isEmpty()) {
            log.error("Recipient email cannot be empty");
            return false;
        }

        Properties prop = new Properties();
        prop.put("mail.smtp.host", SMTP_HOST);
        prop.put("mail.smtp.port", SMTP_PORT);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); // TLS
        prop.put("mail.smtp.ssl.protocols", "TLSv1.2"); // Added security

        Session session = Session.getInstance(prop,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject != null ? subject : DEFAULT_SUBJECT);
            message.setText(messageText);

            Transport.send(message);

            log.debug("Email sent successfully to: {}", recipientEmail);
            return true;

        } catch (MessagingException e) {
            log.error("Failed to send email to: {}", recipientEmail);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Original method maintained for backward compatibility
     * Sends email with default subject
     *
     * @param recipientEmail The email address of the recipient
     * @param generatedPassword The generated password to include in the email
     * @return true if email was sent successfully, false otherwise
     */
    public boolean sendEmail(String recipientEmail, String generatedPassword) {
        String messageText = "Dear User,\n\nYour temporary password is: " + generatedPassword +
                "\n\nPlease use this to log in." +
                "\n\nBest regards,\nYamaha Motors";
        return sendEmail(recipientEmail, DEFAULT_SUBJECT, messageText);
    }
}