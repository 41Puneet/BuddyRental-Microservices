package com.payment_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class BookingResponseDTO {

    private UUID bookingId;
    private UUID userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalAmount;

    public BookingResponseDTO() {
    }

    public BookingResponseDTO(UUID bookingId,
                              UUID userId,
                              LocalDateTime startDate,
                              LocalDateTime endDate,
                              Double totalAmount) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}