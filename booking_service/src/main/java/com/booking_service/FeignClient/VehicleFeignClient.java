package com.booking_service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;
import com.booking_service.DTO.VehicleResponseDTO;


@FeignClient(name="vehicle-service")
public interface VehicleFeignClient{
    @GetMapping("/api/vehicles/{vehicleId}")
    VehicleResponseDTO getVehicleById(@PathVariable("vehicleId") UUID vehicleId);
}
