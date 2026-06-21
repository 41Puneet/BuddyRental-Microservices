package com.booking_service.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.UUID;
import com.booking_service.DTO.BookingRequestDTO;
import com.booking_service.DTO.BookingResponseDTO;

public interface BookingService {
    Page<BookingResponseDTO>findByUserId(UUID userId,Pageable pageable);

    Page<BookingResponseDTO>findByVehicleId(UUID vehicleId,Pageable pageable);

    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO,UUID userId);

    BookingResponseDTO updateBooking(UUID bookingId,BookingRequestDTO bookingRequestDTO);

    BookingResponseDTO cancelBooking(UUID bookingId);

    BookingResponseDTO getBookingById(UUID bookingId);


}
