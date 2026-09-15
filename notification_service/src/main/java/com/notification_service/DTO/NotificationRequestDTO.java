package com.notification_service.DTO;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationRequestDTO {

    @NotNull(message = "userId is required")
    private UUID userId;

    @NotBlank(message = "type is required")
    private String type; // e.g. BOOKING_CREATED, PAYMENT_CONFIRMED, BOOKING_CANCELLED

    @NotBlank(message = "message is required")
    private String message;

    // Optional recipient email — if omitted, the service will look it up
    private String recipientEmail;

    public NotificationRequestDTO() {}

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }
}
