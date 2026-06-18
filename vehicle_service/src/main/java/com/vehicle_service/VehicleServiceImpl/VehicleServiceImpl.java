package com.vehicle_service.VehicleServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vehicle_service.DTO.VehicleRequestDTO;
import com.vehicle_service.DTO.VehicleResponseDTO;
import com.vehicle_service.Entity.Vehicle;
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
    public VehicleResponseDTO createVehicle(VehicleRequestDTO vehicleRequestDTO,UUID ownerId) {
        Optional<Vehicle> vehicle =
                vehicleRepository.findByVehicleNumber(vehicleRequestDTO.getVehicleNumber());
        if (vehicle.isPresent()) {
            logger.error("vehicle already present");
            throw new IllegalArgumentException("Vehicle already exists with number: " + vehicleRequestDTO.getVehicleNumber());
        }
    Vehicle newVehicle = new Vehicle();
    newVehicle.setVehicleNumber(vehicleRequestDTO.getVehicleNumber());
    newVehicle.setOwnerId(ownerId);
    newVehicle.setBrand(vehicleRequestDTO.getBrand());
    newVehicle.setModel(vehicleRequestDTO.getModel());
    newVehicle.setVehicleType(vehicleRequestDTO.getVehicleType());
    newVehicle.setFuelType(vehicleRequestDTO.getFuelType());
    newVehicle.setTransmissionType(vehicleRequestDTO.getTransmissionType());
    newVehicle.setPricePerDay(vehicleRequestDTO.getPricePerDay());
    newVehicle.setAdvancePayment(vehicleRequestDTO.getAdvancePayment());
    newVehicle.setSecurityPrice(vehicleRequestDTO.getSecurityPrice());
    newVehicle.setManufacturingYear(vehicleRequestDTO.getManufacturingYear());
    newVehicle.setCity(vehicleRequestDTO.getCity());
    newVehicle.setIsAvailable(vehicleRequestDTO.getAvailable());

    Vehicle savedvehicle=vehicleRepository.save(newVehicle);
      logger.info("vehicle created successfully");
        return mapToVehicleDTO(savedvehicle);
    }
private VehicleResponseDTO mapToVehicleDTO(Vehicle vehicle){
    if(vehicle==null)return null;
    VehicleResponseDTO vehicleDTO=new VehicleResponseDTO();
    vehicleDTO.setVehicleNumber(vehicle.getVehicleNumber());
    vehicleDTO.setOwnerId(vehicle.getOwnerId());
    vehicleDTO.setBrand(vehicle.getBrand());
    vehicleDTO.setModel(vehicle.getModel());
    vehicleDTO.setPricePerDay(vehicle.getPricePerDay());
    vehicleDTO.setSecurityPrice(vehicle.getSecurityPrice());
    vehicleDTO.setAdvancePayment(vehicle.getAdvancePayment());
    vehicleDTO.setManufacturingYear(vehicle.getManufacturingYear());
    vehicleDTO.setCity(vehicle.getCity());
    vehicleDTO.setVehicleType(vehicle.getVehicleType());
     vehicleDTO.setFuelType(vehicle.getFuelType());
    vehicleDTO.setTransmissionType(vehicle.getTransmissionType());
    vehicleDTO.setIsAvailable(vehicle.getIsAvailable());
    return vehicleDTO;
}
    @Override
    public void deleteVehicle(String vehicleNumber) {
        Optional<Vehicle>vehicle=vehicleRepository.findByVehicleNumber(vehicleNumber);
        if(vehicle.isEmpty()){
            logger.warn("vehicle not found{}:",vehicleNumber);
            throw new IllegalArgumentException("Vehicle not found with number:"+vehicleNumber);
        }
        vehicleRepository.delete(vehicle.get());
        logger.info("vehicle deleted successfully");
    }

    @Override
    public Page<VehicleResponseDTO> findByBrand(String brand, Pageable pageable) {
        Pageable page=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        Page<Vehicle>vehicles=vehicleRepository.findByBrand(brand, page);
        logger.info("vehicle found successfully");
        return vehicles.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleResponseDTO> findByCity(String city, Pageable pageable) {
        Pageable page=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        Page<Vehicle>vehicles=vehicleRepository.findByCity(city, page);
        logger.info("Vehicle found successfully in the city{}",city);
        return vehicles.map(this::mapToVehicleDTO);
    }

    @Override
    public List<VehicleResponseDTO> findByFuelTypeAndTransmissionType(FuelType fuelType,
            TransmissionType transmissionType) {
        List<Vehicle>vehicle=vehicleRepository.findByFuelTypeAndTransmissionType(fuelType, transmissionType);
        logger.info("vehicle found successfully");
        return vehicle.stream().map(this::mapToVehicleDTO).toList();
    }

    @Override
    public List<VehicleResponseDTO> findByFuelTypeAndVehicleType(FuelType fuelType, VehicleType vehicleType) {
        List<Vehicle>vehicle=vehicleRepository.findByFuelTypeAndVehicleType(fuelType, vehicleType);
        logger.info("vehicle found successfully with fueltype{} & vehicleType{}",fuelType,vehicleType);
       return vehicle.stream().map(this::mapToVehicleDTO).toList();
    }

    @Override
    public Page<VehicleResponseDTO> findByManufacturingYear(Integer manufacturingYear, Pageable pageable) {
        Pageable page=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        Page<Vehicle>vehicle=vehicleRepository.findByManufacturingYear(manufacturingYear, page);
        logger.info("vehicle found successfully with manufacturing year{}",manufacturingYear);
        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public Page<VehicleResponseDTO> findByModel(String brand, Pageable pageable) {
        Pageable page=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        Page<Vehicle>vehicle=vehicleRepository.findByBrand(brand, page);
        logger.info("vehicle found successfully with model{}",brand);
        return vehicle.map(this::mapToVehicleDTO);

    }

    @Override
    public Page<VehicleResponseDTO> findByPriceBetween(int minPrice, int maxPrice, Pageable pageable) {
        Pageable pageables=PageRequest.of(pageable.getPageNumber(),pageable.getPageSize());
        Page<Vehicle>vehicle=vehicleRepository.findByPricePerDayBetween((double) minPrice, (double) maxPrice, pageables);
        logger.info("vehicles fetched successfully between {} &{}",minPrice,maxPrice);
        return vehicle.map(this::mapToVehicleDTO);
    }

    @Override
    public List<VehicleResponseDTO> findByTransmissionType(TransmissionType transmissionType) {
        List<Vehicle>vehicle=vehicleRepository.findByTransmissionType(transmissionType);
        return vehicle.stream().map(this::mapToVehicleDTO).toList();
    }

    @Override
    public Optional<VehicleResponseDTO> findByVehicleNumber(String VehicleNumber) {
        Optional<Vehicle>vehicle=vehicleRepository.findByVehicleNumber(VehicleNumber);
       if(vehicle.isPresent()){
       return vehicle.map(this::mapToVehicleDTO);
       }
       return Optional.empty();
    }

    @Override
    public VehicleResponseDTO updateVehicle(VehicleRequestDTO vehicleRequestDTO, String vehicleNumber,UUID ownerId) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findByVehicleNumber(vehicleNumber);
        if (vehicleOptional.isEmpty()) {
            logger.warn("vehicle not found for update {}", vehicleNumber);
            throw new IllegalArgumentException("Vehicle not found with number: " + vehicleNumber);
        }
        Vehicle vehicle = vehicleOptional.get();
        vehicle.setVehicleNumber(vehicleRequestDTO.getVehicleNumber());
        vehicle.setBrand(vehicleRequestDTO.getBrand());
        vehicle.setModel(vehicleRequestDTO.getModel());
        vehicle.setVehicleType(vehicleRequestDTO.getVehicleType());
        vehicle.setTransmissionType(vehicleRequestDTO.getTransmissionType());
        vehicle.setPricePerDay(vehicleRequestDTO.getPricePerDay());
        vehicle.setAdvancePayment(vehicleRequestDTO.getAdvancePayment());
        vehicle.setSecurityPrice(vehicleRequestDTO.getSecurityPrice());
        vehicle.setManufacturingYear(vehicleRequestDTO.getManufacturingYear());
        vehicle.setCity(vehicleRequestDTO.getCity());
        vehicle.setFuelType(vehicleRequestDTO.getFuelType());
        vehicle.setIsAvailable(vehicleRequestDTO.getAvailable());
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        logger.info("vehicle updated successfully");
        return mapToVehicleDTO(updatedVehicle);
    }
    
}
