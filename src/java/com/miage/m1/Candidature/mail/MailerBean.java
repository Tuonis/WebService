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
import javax.mail.internet.MimeBodyPart;

import javax.mail.internet.MimeMessage;

/**
 *
 * Classe permettant l'envoie des mails
 * 
 * @author Tuonis Home
 */
public class MailerBean {
    
    
      
    /**
     * Méthode static permettant l'envoie d'un mail selon des paramètres
     * 
     * @param destination : l'adresse email du destinataire du message
     * @param sujet : le titre du mail
     * @param contenu : le contenu du mail
     */
   public static void sendMail(String destination, String sujet, String contenu){  

                //les paramètres du compte gmail utilisé
                //le compte a été crée juste pour ce projet
                final String username = "xmlcandidature@gmail.com";
		final String password = "miage2013";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                props.put("mail.smtp.ssl.trust", false);
                
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
                        /*message.setText("<html>");
			message.setText(contenu);
                        message.setText("</html>");*/
                        //message.setContent("<html>"+contenu+"</html>", "text/html");
                        message.setContent(contenu, "text/html;charset=UTF-8");
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

    
}
