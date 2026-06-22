package com.payment_service.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.payment_service.Enum.PaymentStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name="payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID paymentId;
    @Column(nullable=false)
    private UUID bookingId;
    @Column(nullable=false)
    private UUID userId;
    @Column(unique=true)
    private String transactionId;
    @Column(nullable=false)
    private Double amount;
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public Payment(){

    }
    public Payment(UUID paymentId,UUID bookingId,UUID userId,String transactionId,PaymentStatus paymentStatus,LocalDateTime createdAt,LocalDateTime updatedAt,Double amount){
        this.paymentId=paymentId;
        this.bookingId=bookingId;
        this.userId=userId;
        this.transactionId=transactionId;
        this.paymentStatus=paymentStatus;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
        this.amount=amount;
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
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @PrePersist
public void onCreate() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
}

@PreUpdate
public void onUpdate() {
    updatedAt = LocalDateTime.now();
}
}
