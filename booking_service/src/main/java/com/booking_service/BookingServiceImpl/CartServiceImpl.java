package com.booking_service.BookingServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

import com.booking_service.DTO.CartItemRequestDTO;
import com.booking_service.DTO.CartResponseDTO;
import com.booking_service.DTO.VehicleResponseDTO;
import com.booking_service.Entity.Booking;
import com.booking_service.FeignClient.VehicleFeignClient;
import com.booking_service.Repository.BookingRepository;
import com.booking_service.Service.CartService;

public class CartServiceImpl implements CartService {

    private final VehicleFeignClient vehicleFeign;

    private final BookingRepository bookingRepository;
    
    private final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);
    public CartServiceImpl(VehicleFeignClient vehicleFeign,BookingRepository bookingRepository){
        this.vehicleFeign=vehicleFeign;
        this.bookingRepository=bookingRepository;
    }
    @Override
    public CartResponseDTO addToCart(CartItemRequestDTO request) {
     VehicleResponseDTO vehicle=vehicleFeign.getVehicleById(request.getVehicleId());
           
        return null;
    }

    @Override
    public void clearCart(UUID userID) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public CartResponseDTO getCart(UUID userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void removeFromCart(UUID cartItemId, UUID userId) {
        // TODO Auto-generated method stub
        
    }
     private void checkAvailability(UUID vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> overlapping = bookingRepository.findOverlappingBooking(vehicleId, startDate, endDate);
        if (!overlapping.isEmpty()) {
            logger.warn("vehicle {} is already booked between {} and {}", vehicleId, startDate, endDate);
            throw new IllegalArgumentException("Vehicle is not available for the selected range");
        }
    }
    
}
