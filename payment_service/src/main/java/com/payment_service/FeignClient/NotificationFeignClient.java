package com.payment_service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.payment_service.DTO.NotificationRequestDTO;

/**
 * Fire-and-forget Feign client to notification_service.
 * ALL calls to this client MUST be wrapped in try/catch — a notification
 * failure must never cause a payment operation to fail.
 */
@FeignClient(name = "notification-service")
public interface NotificationFeignClient {

    @PostMapping("/api/notifications/send")
    void send(@RequestBody NotificationRequestDTO request);
}
