package com.booking_service.Controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking_service.DTO.CartAvailabilityResponseDTO;
import com.booking_service.DTO.CartItemRequestDTO;
import com.booking_service.DTO.CartResponseDTO;
import com.booking_service.Service.CartService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cart")
@Validated
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping({"/items", "/add"})
    public ResponseEntity<CartResponseDTO> addToCart(
            @Valid @RequestBody CartItemRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(request));
    }

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(@RequestHeader("X-User-Id") UUID userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    @GetMapping("/availability")
    public ResponseEntity<CartAvailabilityResponseDTO> checkAvailability(
            @RequestParam UUID vehicleId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        boolean available = cartService.isVehicleAvailable(vehicleId, startDate, endDate);
        String message = available
                ? "Vehicle is available for the selected dates"
                : "Vehicle is not available for the selected dates";
        return ResponseEntity.ok(new CartAvailabilityResponseDTO(
                vehicleId, startDate, endDate, available, message));
    }

    @DeleteMapping("/items/{cartItemId}")
    public ResponseEntity<Void> removeFromCart(
            @PathVariable UUID cartItemId,
            @RequestHeader("X-User-Id") UUID userId) {
        cartService.removeFromCart(cartItemId, userId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@RequestHeader("X-User-Id") UUID userId) {
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
