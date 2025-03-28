package com.web.Application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.Application.entity.User;

import org.springframework.mail.SimpleMailMessage;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendBudgetNotification(User user, String alertMessage) {
        // Send email to the user
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail()); // User's email
        message.setSubject("Budget Alert");
        message.setText(alertMessage);
        emailSender.send(message);
    }
}
