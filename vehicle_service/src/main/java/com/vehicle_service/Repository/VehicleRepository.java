package com.vehicle_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vehicle_service.Entity.Vehicle;
import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle,String> {

    List<Vehicle> findVehicleByCity(String city);

}
