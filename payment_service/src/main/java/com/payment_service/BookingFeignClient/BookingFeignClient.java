package com.payment_service.BookingFeignClient;

import java.util.UUID;
import com.payment_service.Enum.BookingStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.payment_service.DTO.BookingResponseDTO;

@FeignClient(name="booking-service")
public interface BookingFeignClient {
    @GetMapping("/api/bookings/{bookingId}")
    BookingResponseDTO getBookingById(@PathVariable("bookingId") UUID bookingId);

@PutMapping("/api/bookings/updateStatus/{bookingId}")
BookingResponseDTO updateBookingStatus(@PathVariable("bookingId") UUID bookingId, @RequestParam("status") BookingStatus status);
}
