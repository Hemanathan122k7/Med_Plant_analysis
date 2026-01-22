package com.medicinal.plant.service;

/**
 * Email Service Interface
 */
public interface EmailService {

    void sendWelcomeEmail(String to, String username);

    void sendPasswordResetEmail(String to, String resetToken);

    void sendFeedbackReplyEmail(String to, String subject, String message);
}
