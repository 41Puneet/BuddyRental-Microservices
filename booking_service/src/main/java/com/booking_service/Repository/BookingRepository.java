package com.booking_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.booking_service.Entity.Booking;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking,UUID> {
    

}
