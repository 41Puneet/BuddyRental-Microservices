package com.booking_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;


import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
    
    @NotNull
    private UUID userId;
    @NotNull
    private UUID vehicleId;
    @Future
    @NotNull
    private LocalDateTime startDate;
    @Future
    @NotNull
    private LocalDateTime endDate;
    

    public BookingRequestDTO(){

    }
    public BookingRequestDTO(UUID userId,UUID vehicleId,LocalDateTime startDate,LocalDateTime endDate){
        this.userId=userId;
        this.vehicleId=vehicleId;
        this.startDate=startDate;
        this.endDate=endDate;
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

    
}
