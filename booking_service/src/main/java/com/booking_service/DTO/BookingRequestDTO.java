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
    
}
