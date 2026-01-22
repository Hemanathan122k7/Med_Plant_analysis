package com.medicinal.plant.service.impl;

import com.medicinal.plant.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Email Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    @SuppressWarnings("unused") // Will be used when email sending is fully implemented
    private final JavaMailSender mailSender;

    @Override
    public void sendWelcomeEmail(String to, String username) {
        log.info("Sending welcome email to: {}", to);
        // Email sending logic here
    }

    @Override
    public void sendPasswordResetEmail(String to, String resetToken) {
        log.info("Sending password reset email to: {}", to);
        // Email sending logic here
    }

    @Override
    public void sendFeedbackReplyEmail(String to, String subject, String message) {
        log.info("Sending feedback reply to: {}", to);
        // Email sending logic here
    }
}
