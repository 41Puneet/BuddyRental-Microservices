package com.booking_service.Controller;

import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.booking_service.DTO.BookingResponseDTO;
import com.booking_service.DTO.BookingRequestDTO;
import com.booking_service.Service.BookingService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/bookings")
@Validated
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService){
        this.bookingService = bookingService;
    }

    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO request, @RequestHeader("X-User-Id") UUID userId){

        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(request, userId));
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> updateBooking(@PathVariable UUID bookingId,@Valid @RequestBody BookingRequestDTO request){

        return ResponseEntity.ok(bookingService.updateBooking(bookingId, request));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> cancelBooking( @PathVariable UUID bookingId){

        return ResponseEntity.ok(bookingService.cancelBooking(bookingId));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponseDTO> getBookingById(@PathVariable UUID bookingId){

        return ResponseEntity.ok( bookingService.getBookingById(bookingId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByUser( @PathVariable UUID userId,Pageable pageable){

        return ResponseEntity.ok(bookingService.findByUserId(userId, pageable));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<Page<BookingResponseDTO>> getBookingsByVehicle(@PathVariable UUID vehicleId,Pageable pageable){

        return ResponseEntity.ok(bookingService.findByVehicleId(vehicleId, pageable));
    }
}
