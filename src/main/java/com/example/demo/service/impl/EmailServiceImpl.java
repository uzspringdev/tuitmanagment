package com.example.demo.service.impl;

import com.example.demo.model.EmailDetails;
import com.example.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}") private String sender;
    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


    @Override
    public String sendSimpleEmail(EmailDetails emailDetails) {

        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailDetails.getRecipient());
            simpleMailMessage.setSubject(emailDetails.getSubject());
            simpleMailMessage.setText(emailDetails.getMsgBody());
            javaMailSender.send(simpleMailMessage);
            return "Mail Sent Successfully";
        }catch (Exception e){
            return "Error While Sending Mail";
        }
    }

    @Override
    public String sendEmailWithAttachment(EmailDetails emailDetails) {
        return null;
    }
}
