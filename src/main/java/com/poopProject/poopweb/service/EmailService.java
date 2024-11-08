package com.poopProject.poopweb.service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    JavaMailSender javaMailSender;

    public void sendResetPasswordEmail(String to, String resetLink){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject("Password Reset Request");
        simpleMailMessage.setText("To reset your password, click the link below:\n" + resetLink);
        javaMailSender.send(simpleMailMessage);
    }
}
