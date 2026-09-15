package com.notification_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notification_service.DTO.NotificationRequestDTO;
import com.notification_service.Service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Fire-and-forget endpoint: always returns 200 OK immediately.
     * Callers (booking_service, payment_service) wrap this in try/catch so a
     * notification failure never propagates back to the end user.
     */
    @PostMapping("/send")
    public ResponseEntity<Void> send(@Valid @RequestBody NotificationRequestDTO request) {
        notificationService.send(request);
        return ResponseEntity.ok().build();
    }
}
