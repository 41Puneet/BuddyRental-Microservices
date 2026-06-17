package com.vehicle_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicle_service.Entity.Vehicle;
import com.vehicle_service.Enums.TransmissionType;  
import org.springframework.data.domain.Page;
import java.util.List;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.VehicleType;
import java.util.Optional;
import org.springframework.data.domain.Pageable;



public interface VehicleRepository extends JpaRepository<Vehicle,String> {

    Page<Vehicle> findByCity(String city,Pageable pageable);
    List<Vehicle>findByTransmissionType(TransmissionType transmissionType);

    List<Vehicle>findByFuelTypeAndVehicleType(FuelType fuelType,VehicleType vehicleType);

    List<Vehicle>findByFuelTypeAndTransmissionType(FuelType fuelType,TransmissionType transmissionType);

    Optional<Vehicle>findByVehicleNumber(String VehicleNumber);

    Page<Vehicle>findByManufacturingYear(Integer manufacturingYear,Pageable pageable);

    Page<Vehicle>findByBrand(String brand,Pageable pageable);

    Page<Vehicle>findByPriceBetween(int minPrice,int maxPrice,Pageable pageable);

    Page<Vehicle>findByModel(String model,Pageable pageable);

}
