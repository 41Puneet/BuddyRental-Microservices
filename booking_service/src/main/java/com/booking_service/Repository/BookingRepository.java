package com.booking_service.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking_service.Entity.Booking;
import java.util.List;
import feign.Param;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface BookingRepository extends JpaRepository<Booking,UUID> {
    Page<Booking>findByUserId(UUID userId,Pageable pageable);

    Page<Booking>findByVehicleId(UUID vehicleId,Pageable pageable);

    @Query("""
            SELECT b FROM Booking b
            WHERE b.vehicleId=:vehicleId
            AND b.bookingStatus IN(
            com.booking_service.Enums.BookingStatus.PENDING,
            com.booking_service.Enums.BookingStatus.CONFIRMED
            )
            AND :startDate<b.endDate
            AND :endDate>b.startDate
            """)
List<Booking>findOverlappingBooking(@Param ("vehicleId")UUID vehicleId,@Param ("startDate")LocalDateTime startDate,@Param("endDate")LocalDateTime endDate);


      @Query("""
        SELECT b FROM Booking b
        WHERE b.vehicleId=:vehicleId
        AND b.bookingId<> :bookingId
        AND b.bookingStatus IN(
        com.booking_service.Enums.BookingStatus.PENDING,
        com.booking_service.Enums.BookingStatus.CONFIRMED
        )
        AND :startDate<b.endDate
        AND :endDate>b.startDate
        """)
        List<Booking>findOverlappingBookingforUpdateBookings(@Param ("vehicleId")UUID vehicleId,@Param ("bookingId")UUID bookingId,@Param ("startDate")LocalDateTime startDate,@Param("endDate")LocalDateTime endDate);
}
