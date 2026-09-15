package com.booking_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

public class CartAvailabilityResponseDTO {

    private UUID vehicleId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean available;
    private String message;

    public CartAvailabilityResponseDTO() {
    }

    public CartAvailabilityResponseDTO(UUID vehicleId, LocalDateTime startDate,
                                      LocalDateTime endDate, boolean available,
                                      String message) {
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.available = available;
        this.message = message;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
