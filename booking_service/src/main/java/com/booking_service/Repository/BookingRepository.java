package com.booking_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.booking_service.Entity.Booking;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking,UUID> {
    Page<Booking>findByUserId(UUID userId,Pageable pageable);

    Page<Booking>findByVehicleId(UUID vehicleId,Pageable pageable);

    List<Booking>findByVehicleId(UUID vehicleId);


}
