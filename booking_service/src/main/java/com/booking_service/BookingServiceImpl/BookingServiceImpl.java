package com.booking_service.BookingServiceImpl;

import java.util.List;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booking_service.DTO.VehicleResponseDTO;
import com.booking_service.DTO.BookingRequestDTO;
import com.booking_service.DTO.BookingResponseDTO;
import com.booking_service.FeignClient.VehicleFeignClient;
import com.booking_service.Repository.BookingRepository;
import com.booking_service.Service.BookingService;

import jakarta.transaction.Transactional;

import com.booking_service.Entity.Booking;
import com.booking_service.Enums.BookingStatus;


@Service
@Transactional
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
      
       checkAvailability(vehicle.getVehicleId(), bookingRequestDTO.getStartDate(), bookingRequestDTO.getEndDate());
        Booking saved =bookingRepository.save(booking);
        logger.info("booking created successfully for the vehicle{}"+vehicle.getVehicleId());
        return mapToBookingDTO(saved,vehicle);
}
private void checkAvailability(UUID vehicleId,LocalDateTime startDate,LocalDateTime endDate){
List<Booking>overlapping=bookingRepository.findOverlappingBooking(vehicleId, startDate, endDate);
if(!overlapping.isEmpty()){
    logger.warn("vehicle{} is already booked between {} and {}"+vehicleId,startDate,endDate);
    throw new IllegalArgumentException("Vehicle is not available for the selected range");

}
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
        Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
        if (bookingOptional.isPresent()) {
            Booking booking = bookingOptional.get();
            if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
    throw new IllegalArgumentException(
            "Booking is already cancelled");
}
            booking.setBookingStatus(BookingStatus.CANCELLED);
            Booking updatedBooking = bookingRepository.save(booking);
            VehicleResponseDTO vehicle = vehicleFeignClient.getVehicleById(updatedBooking.getVehicleId());
            logger.info("booking marked as cancelled with bookingId{}",bookingId);
            return mapToBookingDTO(updatedBooking, vehicle);
        }
        logger.warn("booking not found with bookingId{}",bookingId);
        throw new IllegalArgumentException("Booking not found with id"+bookingId);
    }


    @Override
    public Page<BookingResponseDTO> findByUserId(UUID userId, Pageable pageable) {
       Page<Booking>bookings=bookingRepository.findByUserId(userId, pageable);
        if(bookings.isEmpty()){
            logger.warn("bookings not found with userId{}",userId);
        throw new IllegalArgumentException("Booking not found with userId"+userId);
        }
        return bookings.map(booking->{
            VehicleResponseDTO vehicle=vehicleFeignClient.getVehicleById(booking.getVehicleId());
       return mapToBookingDTO(booking, vehicle);
        });
    }

    @Override
    public Page<BookingResponseDTO> findByVehicleId(UUID vehicleId, Pageable pageable) {
       Page<Booking>bookings=bookingRepository.findByVehicleId(vehicleId, pageable);
       if(bookings.isEmpty()){
            logger.warn("bookings not found with vehicleId{}",vehicleId);
        throw new IllegalArgumentException("Booking not found with vehicleId"+vehicleId);
       }
     return bookings.map(booking->{
        VehicleResponseDTO vehicle=vehicleFeignClient.getVehicleById(booking.getVehicleId());
       return mapToBookingDTO(booking, vehicle);
     });
    }

    @Override
    public BookingResponseDTO getBookingById(UUID bookingId) {
       Optional<Booking> bookingOptional = bookingRepository.findById(bookingId);
       if (bookingOptional.isPresent()) {
           Booking booking = bookingOptional.get();
           VehicleResponseDTO vehicle = vehicleFeignClient.getVehicleById(booking.getVehicleId());
           return mapToBookingDTO(booking, vehicle);
       }
       logger.warn("booking not found with bookingId{}", bookingId);
       throw new IllegalArgumentException("Booking not found with id"+bookingId);
    }

    @Override
public BookingResponseDTO updateBooking(
        UUID bookingId,
        BookingRequestDTO bookingRequestDTO) {

    Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> {
                logger.warn(
                        "Booking not found with bookingId={}",
                        bookingId);

                return new IllegalArgumentException(
                        "Booking not found with id " + bookingId);
            });

    if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
        throw new IllegalArgumentException(
                "Cancelled booking cannot be updated");
    }

    if (bookingRequestDTO.getEndDate()
            .isBefore(bookingRequestDTO.getStartDate())) {

        logger.warn(
                "Invalid booking dates. startDate={}, endDate={}",
                bookingRequestDTO.getStartDate(),
                bookingRequestDTO.getEndDate());

        throw new IllegalArgumentException(
                "End date must be after start date");
    }

    if (!booking.getVehicleId().equals(bookingRequestDTO.getVehicleId())
            || !booking.getStartDate().equals(bookingRequestDTO.getStartDate())
            || !booking.getEndDate().equals(bookingRequestDTO.getEndDate())) {

        checkAvailabilityforUpdate(
                bookingRequestDTO.getVehicleId(),
                bookingId,
                bookingRequestDTO.getStartDate(),
                bookingRequestDTO.getEndDate());
    }

    VehicleResponseDTO vehicle =
            vehicleFeignClient.getVehicleById(
                    bookingRequestDTO.getVehicleId());

    booking.setVehicleId(
            bookingRequestDTO.getVehicleId());

    booking.setStartDate(
            bookingRequestDTO.getStartDate());

    booking.setEndDate(
            bookingRequestDTO.getEndDate());

    long days = ChronoUnit.DAYS.between(
            bookingRequestDTO.getStartDate().toLocalDate(),
            bookingRequestDTO.getEndDate().toLocalDate());

    if (days <= 0) {
        days = 1;
    }

    booking.setTotalAmount(
            vehicle.getPricePerDay() * days);

    Booking updatedBooking =
            bookingRepository.save(booking);

    logger.info(
            "Booking updated successfully. bookingId={}",
            bookingId);

    return mapToBookingDTO(
            updatedBooking,
            vehicle);
}
    private void checkAvailabilityforUpdate(UUID vehicleId,UUID bookingId,LocalDateTime startDate,LocalDateTime endDate){
        List<Booking>overlapping=bookingRepository.findOverlappingBookingforUpdateBookings(vehicleId, bookingId, startDate, endDate);
        if(!overlapping.isEmpty()){
            logger.warn("vehicle{} is already booked between {} and {}"+vehicleId,startDate,endDate);
         throw new IllegalArgumentException("Vehicle is not available for the selected range");

        }
    }
}
