package com.booking_service.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public class BookingRequestDTO {
    
    
    @NotNull
    private UUID vehicleId;
    @Future
    @NotNull
    private LocalDateTime startDate;
    @Future
    @NotNull
    private LocalDateTime endDate;
    //3a07b8c9-35df-4029-b023-c227b3c44cdb {
/*  "vehicleNumber":"UP27E9144",
  "startDate":"2026-06-25T10:00:00",
  "endDate":"2026-06-26T10:00:00"

  4771d141-cf6a-44fe-a24f-c9bea88ecce0
}/ */

    public BookingRequestDTO(){

    }
    public BookingRequestDTO(UUID vehicleId,LocalDateTime startDate,LocalDateTime endDate){
       
        this.vehicleId=vehicleId;
        this.startDate=startDate;
        this.endDate=endDate;
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
