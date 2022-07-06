package com.example.demo.service;

import com.example.demo.model.EmailDetails;

public interface EmailService {

    String sendSimpleEmail(EmailDetails emailDetails);

    String sendEmailWithAttachment(EmailDetails emailDetails);
}
