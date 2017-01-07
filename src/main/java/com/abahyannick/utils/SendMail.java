package com.abahyannick.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.abahyannick.services.UserService;

@Component
public class SendMail {
	
	Logger logger = Logger.getLogger(SendMail.class);
	
	public void sendMail(String destination, String subject, String text){

		
		
		final String username = "abahyann@gmail.com";
		final String password = "Michele2016";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destination));
			message.setSubject(subject);
			message.setText(text);

			Transport.send(message);

	         logger.info("Sent message successfully....");

		      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }
	}

}
