package com.vehicle_service.Controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import com.vehicle_service.DTO.VehicleRequestDTO;
import com.vehicle_service.DTO.VehicleResponseDTO;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;
import com.vehicle_service.Service.VehicleService;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService){
        this.vehicleService = vehicleService;
    }

    @PostMapping("/create")
    public VehicleResponseDTO createVehicle(@RequestBody VehicleRequestDTO vehicleRequestDTO){
        return vehicleService.createVehicle(vehicleRequestDTO);
    }

    @GetMapping("/city")
public ResponseEntity<?> getVehicleByCity(
        @RequestParam String city,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);

    return ResponseEntity.ok(
            vehicleService.findByCity(city, pageable));
}
    @GetMapping("/transmissionType")
    public ResponseEntity<?>getVehicleByTransmissionType(@RequestParam TransmissionType transmissionType){
        return ResponseEntity.ok(
        vehicleService.findByTransmissionType(transmissionType));
    }
    @GetMapping("/fuelType")
public ResponseEntity<?> getVehicleByFuelTypeAndVehicleType(
        @RequestParam FuelType fuelType,
        @RequestParam VehicleType vehicleType) {

    return ResponseEntity.ok(
            vehicleService.findByFuelTypeAndVehicleType(
                    fuelType,
                    vehicleType));
}

    @GetMapping("/fuelTransmission")
public ResponseEntity<?> getVehicleByFuelTypeAndTransmissionType(
        @RequestParam FuelType fuelType,
        @RequestParam TransmissionType transmissionType) {

    return ResponseEntity.ok(
            vehicleService.findByFuelTypeAndTransmissionType(
                    fuelType,
                    transmissionType));
}

    @GetMapping("/{vehicleNumber}")
public ResponseEntity<?> getVehicleByVehicleNumber(
        @PathVariable String vehicleNumber) {

    return ResponseEntity.ok(
            vehicleService.findByVehicleNumber(vehicleNumber));
}

    @GetMapping("/manufacturingYear")
    public ResponseEntity<?> getVehicleByManufacturingYear(@RequestParam Integer manufacturingYear) {
        return ResponseEntity.ok(vehicleService.findByManufacturingYear(manufacturingYear));
    }

    @GetMapping("/brand")
    public ResponseEntity<?> getVehicleByBrand(@RequestParam String brand) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/priceBetween")
    public ResponseEntity<?> getVehicleByPriceBetween(@RequestParam Integer minPrice, @RequestParam Integer maxPrice) {
        return ResponseEntity.ok().build();
    }
    @GetMapping("/model")
    public ResponseEntity<?> getVehicleByModel(@RequestParam String model) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{vehicleNumber}")
public ResponseEntity<VehicleResponseDTO> updateVehicle(
        @PathVariable String vehicleNumber,
        @RequestBody VehicleRequestDTO dto) {

    return ResponseEntity.ok(
            vehicleService.updateVehicle(dto, vehicleNumber));
}
    @DeleteMapping("/delete")
    public void deleteVehicle(@RequestParam String vehicleNumber){
        vehicleService.deleteVehicle(vehicleNumber);
    }
}
