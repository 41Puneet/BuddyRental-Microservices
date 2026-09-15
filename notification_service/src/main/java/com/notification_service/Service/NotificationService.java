package com.notification_service.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.notification_service.DTO.NotificationRequestDTO;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    private final JavaMailSender mailSender;

    @Value("${notification.mail.from:noreply@buddyrental.in}")
    private String fromAddress;

    public NotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Sends a notification. Currently supports email only.
     * If recipientEmail is blank, the notification is logged but not emailed —
     * a future improvement would be to look up the email from user_service.
     *
     * IMPORTANT: this method must NEVER throw — callers (booking/payment) should
     * never fail because a notification couldn't be sent.
     */
    public void send(NotificationRequestDTO request) {
        logger.info("Notification request: userId={} type={}", request.getUserId(), request.getType());

        if (!StringUtils.hasText(request.getRecipientEmail())) {
            logger.warn("No recipientEmail provided for userId={} type={} — email skipped, notification logged only",
                    request.getUserId(), request.getType());
            return;
        }

        sendEmail(request.getRecipientEmail(), request.getType(), request.getMessage());
    }

    private void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(to);
            message.setSubject("[BuddyRental] " + subject.replace("_", " "));
            message.setText(body);
            mailSender.send(message);
            logger.info("Email sent to {} subject='{}'", to, subject);
        } catch (MailException e) {
            // Log but never propagate — notification failure must not affect business flow
            logger.error("Failed to send email to {} type={}: {}", to, subject, e.getMessage(), e);
        }
    }
}
