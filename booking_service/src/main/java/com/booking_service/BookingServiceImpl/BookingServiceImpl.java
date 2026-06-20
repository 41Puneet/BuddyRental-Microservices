package com.booking_service.BookingServiceImpl;

import java.time.Duration;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.booking_service.DTO.VehicleResponseDTO;
import com.booking_service.DTO.BookingRequestDTO;
import com.booking_service.DTO.BookingResponseDTO;
import com.booking_service.FeignClient.VehicleFeignClient;
import com.booking_service.Repository.BookingRepository;
import com.booking_service.Service.BookingService;
import com.booking_service.Entity.Booking;

public class BookingServiceImpl implements BookingService{
  
private final Logger logger=LoggerFactory.getLogger(BookingServiceImpl.class);
private final BookingRepository bookingRepository;
private final VehicleFeignClient vehicleFeignClient;

public BookingServiceImpl(BookingRepository bookingRepository,VehicleFeignClient vehicleFeignClient){
    this.bookingRepository=bookingRepository;
    this.vehicleFeignClient=vehicleFeignClient;
}


      @Override
    public BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO,UUID userId) {
        VehicleResponseDTO vehicle=vehicleFeignClient.getVehicleById(bookingRequestDTO.getVehicleId());

        Booking booking=new Booking();
        booking.setUserId(userId);
        booking.setVehicleId(vehicle.getVehicleId());
        booking.setStartDate(bookingRequestDTO.getStartDate());
        booking.setEndDate(bookingRequestDTO.getEndDate());
        booking.setTotalAmount(vehicle.getPricePerDay()*(bookingRequestDTO.getEndDate()-bookingRequestDTO.getStartDate()));
        return null;
    }


    @Override
    public BookingResponseDTO cancelBooking(UUID bookingId) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Page<BookingResponseDTO> findByUserId(UUID userId, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<BookingResponseDTO> findByVehicleId(UUID vehicleId, Pageable pageable) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BookingResponseDTO getBookingById(UUID bookingId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BookingResponseDTO updateBooking(UUID bookingId, BookingRequestDTO bookingRequestDTO) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
