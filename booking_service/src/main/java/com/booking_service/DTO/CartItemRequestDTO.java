package com.booking_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public class CartItemRequestDTO {
    @NotNull
    private UUID vehicleId;
    @NotNull
    private LocalDateTime startDate;
    @NotNull
    private LocalDateTime endDate;
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
