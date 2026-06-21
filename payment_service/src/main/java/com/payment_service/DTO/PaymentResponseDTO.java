package com.payment_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.payment_service.Enum.PaymentStatus;

public class PaymentResponseDTO {

    private UUID paymentId;
    private UUID bookingId;
    private UUID userId;
    private String transactionId;
    private Double amount;
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;

    private BookingResponseDTO booking;

    public PaymentResponseDTO() {
    }

    public PaymentResponseDTO(UUID paymentId,
                              UUID bookingId,
                              UUID userId,
                              String transactionId,
                              Double amount,
                              PaymentStatus paymentStatus,
                              LocalDateTime createdAt,
                              BookingResponseDTO booking) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.userId = userId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.booking = booking;
    }

    public UUID getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(UUID paymentId) {
        this.paymentId = paymentId;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public BookingResponseDTO getBooking() {
        return booking;
    }

    public void setBooking(BookingResponseDTO booking) {
        this.booking = booking;
    }
}