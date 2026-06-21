package com.booking_service.BookingServiceImpl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
import com.booking_service.Enums.BookingStatus;

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
        
        VehicleResponseDTO vehicle =vehicleFeignClient.getVehicleById(bookingRequestDTO.getVehicleId());

         if (bookingRequestDTO.getEndDate()
        .isBefore(
                bookingRequestDTO.getStartDate())) {
          logger.warn("please select valid dates start date={} & end date={}"+bookingRequestDTO.getStartDate(),bookingRequestDTO.getEndDate());
         throw new IllegalArgumentException(
            "End date must be after start date");
}
        long days = ChronoUnit.DAYS.between(bookingRequestDTO.getStartDate().toLocalDate(),bookingRequestDTO.getEndDate().toLocalDate());
        Booking booking=new Booking();

        booking.setUserId(userId);
        booking.setVehicleId(vehicle.getVehicleId());
        booking.setStartDate(bookingRequestDTO.getStartDate());
        booking.setEndDate(bookingRequestDTO.getEndDate());
        booking.setTotalAmount(vehicle.getPricePerDay()*days);
        booking.setBookingStatus(BookingStatus.PENDING);
      
       
       
        Booking saved =bookingRepository.save(booking);
        logger.info("booking created successfully for the vehicle{}"+vehicleFeignClient.getVehicleById(bookingRequestDTO.getVehicleId()));
        return mapToBookingDTO(saved,vehicle);
}
private void checkAvailability(UUID vehicleId,LocalDateTime startDate,LocalDateTime endDate){

}

private BookingResponseDTO mapToBookingDTO(Booking booking,VehicleResponseDTO vehicle){
       BookingResponseDTO response=new BookingResponseDTO();
       response.setBookingId(booking.getBookingId());
       response.setUserId(booking.getUserId());
       response.setStartDate(booking.getStartDate());
       response.setEndDate(booking.getEndDate());
       response.setTotalAmount(booking.getTotalAmount());
       response.setBookingStatus(booking.getBookingStatus());
       response.setVehicle(vehicle);
       return response;
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
