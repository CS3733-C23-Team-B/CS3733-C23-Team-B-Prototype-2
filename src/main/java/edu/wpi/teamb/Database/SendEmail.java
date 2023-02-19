package edu.wpi.teamb.Database;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
  public static void sendEmail(
      String username, String password, String recipient, String subject, String message)
      throws MessagingException {

    // Set up SMTP properties
    Properties properties = new Properties();
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");

    // Get session with username and password authentication
    Session session =
        Session.getInstance(
            properties,
            new Authenticator() {
              protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
              }
            });
    // Create the email message
    Message emailMessage = new MimeMessage(session);
    emailMessage.setFrom(new InternetAddress(username));
    emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
    emailMessage.setSubject(subject);
    emailMessage.setText(message);

    // Send the email
    Transport.send(emailMessage);
  }
}
