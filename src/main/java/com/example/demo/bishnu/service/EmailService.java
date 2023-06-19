package com.example.demo.bishnu.service;

import java.io.File;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.stereotype.Service;


  @Service
  public class EmailService {
// only message send in email
    public boolean sendGmail(String subject, String message, String to) {
   boolean f=false;
     String from= "aryalbishnu@gmail.com";
     //variable for gmail  
     String host="smtp.gmail.com";
     
     //get system properties
     Properties properties= System.getProperties();
     System.out.println("Properties:"+ properties);
     
     //setting information to properties
     
     //host set
     
     properties.put("mail.smtp.host", host);
     properties.put("mail.smtp.port", "465");
     properties.put("mail.smtp.ssl.enable", "true");
     properties.put("mail.smtp.auth", "true");
     
     //get the session object
     
     Session session= Session.getInstance(properties, new Authenticator() {

       @Override
       protected PasswordAuthentication getPasswordAuthentication() {
         
         return new PasswordAuthentication("aryalbishnu3030@gmail.com", "wmrrqedfnglkkqpf");
       }
       
       
     });
     
   
     session.setDebug(true);
     //Compose Message
    
     MimeMessage mimeMessage= new MimeMessage(session);
     try {
       //from email
       mimeMessage.setFrom(from);
       
       //adding recipient message
       mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
       
       //adding subject to message
       mimeMessage.setSubject(subject);
       
       //adding text to message
      
       mimeMessage.setContent(message, "text/html; charset=UTF-8");
       //send message transport class
       
       Transport.send(mimeMessage);
       
       System.out.println("sent Message success--------------");
       f=true;
     } catch (Exception e) {
       e.printStackTrace();
     }
     return f; 
   }
    
    // message and multipart file send in email
    public boolean fileMessageSend(String subject, String message, String to, File file1) throws MessagingException {
      boolean f=false;
      String from= "aryalbishnu@gmail.com"; 
      String host="smtp.gmail.com";      
      Properties properties= System.getProperties();
      System.out.println("Properties:"+ properties);       
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");
      
      Session session= Session.getInstance(properties, new Authenticator() {
      @Override
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication("aryalbishnu3030@gmail.com", "wmrrqedfnglkkqpf");
      }      
    });
      session.setDebug(true);
      MimeMessage mimeMessage= new MimeMessage(session);
      try {
        //from email
        mimeMessage.setFrom(from);
        
        //adding recipient message
        mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        
        //adding subject to message
        mimeMessage.setSubject(subject);
        MimeMultipart mimeMultipart= new MimeMultipart();
        MimeBodyPart text= new MimeBodyPart();
        MimeBodyPart file= new MimeBodyPart();
        text.setText(message);
        file.attachFile(file1);

        mimeMultipart.addBodyPart(text);
        mimeMultipart.addBodyPart(file);
    
        mimeMessage.setContent(mimeMultipart,"text/html; charset=UTF-8");
        Transport.send(mimeMessage);
        System.out.println("sent Message success--------------");
        f=true;
      } catch (Exception e) {
        e.printStackTrace();
      }
      return f; 
  }
    
    }