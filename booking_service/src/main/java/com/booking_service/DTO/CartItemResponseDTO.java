package com.booking_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class CartItemResponseDTO {
    
    private UUID id;
    private UUID vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public CartItemResponseDTO(){

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
