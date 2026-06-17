package com.vehicle_service.Controller;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.vehicle_service.DTO.VehicleRequestDTO;
import com.vehicle_service.DTO.VehicleResponseDTO;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.VehicleType;
import com.vehicle_service.Service.VehicleService;
import com.vehicle_service.Enums.TransmissionType;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/vehicles")
@Validated
public class VehicleController {

    private final VehicleService vehicleService;

   
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public ResponseEntity<VehicleResponseDTO> createVehicle(@Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return ResponseEntity.ok(vehicleService.createVehicle(vehicleRequestDTO));
    }

    @GetMapping("/city")
    public ResponseEntity<Page<VehicleResponseDTO>> getVehicleByCity(
            @RequestParam String city,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.findByCity(city, pageable));
    }

    @GetMapping("/transmissionType")
    public ResponseEntity<?> getVehicleByTransmissionType(@RequestParam TransmissionType transmissionType) {
        return ResponseEntity.ok(vehicleService.findByTransmissionType(transmissionType));
    }

    @GetMapping("/fuelType")
    public ResponseEntity<?> getVehicleByFuelTypeAndVehicleType(
            @RequestParam FuelType fuelType,
            @RequestParam VehicleType vehicleType) {
        return ResponseEntity.ok(vehicleService.findByFuelTypeAndVehicleType(fuelType, vehicleType));
    }

    @GetMapping("/fuelTransmission")
    public ResponseEntity<?> getVehicleByFuelTypeAndTransmissionType(
            @RequestParam FuelType fuelType,
            @RequestParam TransmissionType transmissionType) {
        return ResponseEntity.ok(vehicleService.findByFuelTypeAndTransmissionType(fuelType, transmissionType));
    }

    @GetMapping("/vehicleNumber")
    public ResponseEntity<VehicleResponseDTO> getVehicleByVehicleNumber(@RequestParam String vehicleNumber) {
        Optional<VehicleResponseDTO> vehicle = vehicleService.findByVehicleNumber(vehicleNumber);
        return vehicle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/manufacturingYear")
    public ResponseEntity<Page<VehicleResponseDTO>> getVehicleByManufacturingYear(
            @RequestParam Integer manufacturingYear,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.findByManufacturingYear(manufacturingYear, pageable));
    }

    @GetMapping("/brand")
    public ResponseEntity<Page<VehicleResponseDTO>> getVehicleByBrand(
            @RequestParam String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.findByBrand(brand, pageable));
    }

    @GetMapping("/priceBetween")
    public ResponseEntity<Page<VehicleResponseDTO>> getVehicleByPriceBetween(
            @RequestParam int minPrice,
            @RequestParam int maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.findByPriceBetween(minPrice, maxPrice, pageable));
    }

    @GetMapping("/model")
    public ResponseEntity<Page<VehicleResponseDTO>> getVehicleByModel(
            @RequestParam String model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(vehicleService.findByModel(model, pageable));
    }

    @PutMapping("/update/{vehicleNumber}")
    public ResponseEntity<VehicleResponseDTO> updateVehicle(
            @PathVariable String vehicleNumber,
            @Valid @RequestBody VehicleRequestDTO vehicleRequestDTO) {
        return ResponseEntity.ok(vehicleService.updateVehicle(vehicleRequestDTO, vehicleNumber));
    }

    @DeleteMapping("/{vehicleNumber}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable String vehicleNumber) {
        vehicleService.deleteVehicle(vehicleNumber);
        return ResponseEntity.noContent().build();
    }
}
