package com.vehicle_service.VehicleServiceImpl;

import java.util.List;
import java.util.Optional;
import com.vehicle_service.Entity.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.vehicle_service.DTO.VehicleRequestDTO;
import com.vehicle_service.DTO.VehicleResponseDTO;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;
import com.vehicle_service.Repository.VehicleRepository;
import com.vehicle_service.Service.VehicleService;
 

@Service
public class VehicleServiceImpl implements VehicleService{


    private final VehicleRepository vehicleRepository;

    private final Logger logger = LoggerFactory.getLogger(VehicleServiceImpl.class);

    public VehicleServiceImpl(VehicleRepository vehicleRepository){
    this.vehicleRepository=vehicleRepository;
    }

    @Override
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO) {
        Optional<Vehicle> vehicle =
                vehicleRepository.findByVehicleNumber(vehicleRequestDTO.getVehicleNumber());
        if (vehicle.isPresent()) {
            logger.error("vehicle already present");
            throw new IllegalArgumentException("Vehicle already exists with number: " + vehicleRequestDTO.getVehicleNumber());
        }
    Vehicle newVehicle = new Vehicle();

        return null;
    }

    @Override
    public void deleteVehicle(String vehicleNumber) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Page<VehicleResponseDTO> findByBrand(String brand, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleResponseDTO> findByCity(String city, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VehicleResponseDTO> findByFuelTypeAndTransmissionType(FuelType fuelType,
            TransmissionType transmissionType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VehicleResponseDTO> findByFuelTypeAndVehicleType(FuelType fuelType, VehicleType vehicleType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleResponseDTO> findByManufacturingYear(Integer manufacturingYear, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleResponseDTO> findByModel(String model, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<VehicleResponseDTO> findByPriceBetween(int minPrice, int maxPrice, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<VehicleResponseDTO> findByTransmissionType(TransmissionType transmissionType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<VehicleResponseDTO> findByVehicleNumber(String VehicleNumber) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public VehicleResponseDTO updateVehicle(VehicleRequestDTO vehicleRequestDTO, String vehicleNumber) {
        // TODO Auto-generated method stub
        return null;
    }
    
}