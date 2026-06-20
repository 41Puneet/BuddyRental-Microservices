package com.booking_service.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import com.booking_service.Enums.BookingStatus;

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
@Table(name="bookings")
public class Booking {
    
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private UUID bookingId;
    @Column(nullable=false)
    private UUID userId;
    @Column(nullable=false)
    private UUID vehicleId;
    @Column(nullable=false)
    private LocalDateTime startDate;
    @Column(nullable=false)
    private LocalDateTime endDate;
    @Column(nullable=false)
    private Double totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private BookingStatus bookingStatus;

public Booking(){

}
public Booking(UUID bookingId,UUID userId,UUID vehicleId,LocalDateTime createdAt,LocalDateTime updatedAt,BookingStatus bookingStatus,LocalDateTime startDate,LocalDateTime endDate,Double totalAmount){
    this.bookingId=bookingId;
    this.userId=userId;
    this.vehicleId=vehicleId;
    this.createdAt=createdAt;
    this.updatedAt=updatedAt;
    this.bookingStatus=bookingStatus;
    this.startDate=startDate;
    this.endDate=endDate;
    this.totalAmount=totalAmount;
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
public UUID getVehicleId() {
    return vehicleId;
}
public void setVehicleId(UUID vehicleId) {
    this.vehicleId = vehicleId;
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
public BookingStatus getBookingStatus() {
    return bookingStatus;
}
public void setBookingStatus(BookingStatus bookingStatus) {
    this.bookingStatus = bookingStatus;
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
