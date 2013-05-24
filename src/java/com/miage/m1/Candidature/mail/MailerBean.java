/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miage.m1.Candidature.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

/**
 *
 * @author Tuonis Home
 */
public class MailerBean {
    
    
    public static void sendMail(){  

        
                final String username = "xmlcandidature@gmail.com";
		final String password = "miage2013";
 
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
			message.setFrom(new InternetAddress("xmlcandidature@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("kentish.munusami@gmail.com"));
			message.setSubject("Mail xml");
			message.setText("Yop, l'email que tu vois a été envoyé depuis du code java !"
                                + ", happy ?");
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
    
    /**
     * 
     * @param destination : l'adresse email du destinataire du message
     * @param sujet : le titre du mail
     * @param contenu : le contenu du mail
     */
   public static void sendMail(String destination, String sujet, String contenu){  

        
                final String username = "xmlcandidature@gmail.com";
		final String password = "miage2013";
 
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
			message.setFrom(new InternetAddress("xmlcandidature@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destination));
			message.setSubject(sujet);
			message.setText(contenu);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

    
}
