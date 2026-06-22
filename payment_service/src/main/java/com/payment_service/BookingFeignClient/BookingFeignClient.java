package com.payment_service.BookingFeignClient;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment_service.DTO.BookingResponseDTO;

@FeignClient(name="booking-service")
public interface BookingFeignClient {
    @GetMapping("/api/bookings/{bookingId}")
    BookingResponseDTO getBookingById(@PathVariable UUID bookingId);

@PatchMapping("/updateStatus/{bookingId}")
BookingResponseDTO updateBookingStatus(@PathVariable UUID bookingId, @RequestParam("status") String status);
}
