package com.booking_service.DTO;
import java.util.UUID;
import java.time.LocalDateTime;
import com.booking_service.Enums.BookingStatus;
public class BookingResponseDTO {
 
    private UUID bookingId;
    private UUID userId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double totalAmount;
    private BookingStatus bookingStatus;
    private VehicleResponseDTO vehicle;

    public BookingResponseDTO(){

    }
    public BookingResponseDTO(UUID bookingId,UUID userId,LocalDateTime startDate,LocalDateTime endDate,Double totalAmount,BookingStatus bookingStatus,VehicleResponseDTO vehicle){
        this.bookingId=bookingId;
        this.userId=userId;
        this.startDate=startDate;
        this.endDate=endDate;
         this.totalAmount=totalAmount;
        this.bookingStatus=bookingStatus;
        this.vehicle=vehicle;
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
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }
    public VehicleResponseDTO getVehicle() {
        return vehicle;
    }
    public void setVehicle(VehicleResponseDTO vehicle) {
        this.vehicle = vehicle;
    }

}
