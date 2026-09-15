package com.payment_service.DTO;

import java.util.UUID;

public class NotificationRequestDTO {

    private UUID userId;
    private String type;
    private String message;
    private String recipientEmail;

    public NotificationRequestDTO() {}

    public NotificationRequestDTO(UUID userId, String type, String message) {
        this.userId = userId;
        this.type = type;
        this.message = message;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
}
