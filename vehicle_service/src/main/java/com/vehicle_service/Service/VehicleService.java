package com.vehicle_service.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.vehicle_service.DTO.VehicleRequestDTO;
import com.vehicle_service.DTO.VehicleResponseDTO;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;

public interface VehicleService {
    Page<VehicleResponseDTO>findByCity(String city,Pageable pageable);

    List<VehicleResponseDTO>findByTransmissionType(TransmissionType transmissionType);
    
    List<VehicleResponseDTO>findByFuelTypeAndVehicleType(FuelType fuelType,VehicleType vehicleType);
    
    List<VehicleResponseDTO>findByFuelTypeAndTransmissionType(FuelType fuelType,TransmissionType transmissionType);

    Optional<VehicleResponseDTO>findByVehicleNumber(String VehicleNumber);

    Page<VehicleResponseDTO>findByManufacturingYear(Integer manufacturingYear,Pageable pageable);

    Page<VehicleResponseDTO>findByBrand(String brand,Pageable pageable);

    Page<VehicleResponseDTO>findByPriceBetween(int minPrice,int maxPrice,Pageable pageable);

    Page<VehicleResponseDTO>findByModel(String model,Pageable pageable);

    VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO,UUID ownerId);
 
    VehicleResponseDTO updateVehicle(VehicleRequestDTO vehicleRequestDTO,String vehicleNumber,UUID ownerId);

    void deleteVehicle(String vehicleNumber);

}
