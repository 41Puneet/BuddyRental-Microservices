package com.booking_service.BookingServiceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.booking_service.DTO.CartItemRequestDTO;
import com.booking_service.DTO.CartItemResponseDTO;
import com.booking_service.DTO.CartResponseDTO;
import com.booking_service.DTO.VehicleResponseDTO;
import com.booking_service.Entity.Booking;
import com.booking_service.Entity.Cart;
import com.booking_service.Entity.CartItem;
import com.booking_service.FeignClient.VehicleFeignClient;
import com.booking_service.Repository.BookingRepository;
import com.booking_service.Repository.CartItemRepository;
import com.booking_service.Repository.CartRepository;
import com.booking_service.Service.CartService;

@Service
public class CartServiceImpl implements CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    private final VehicleFeignClient vehicleFeign;
    private final BookingRepository bookingRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartServiceImpl(VehicleFeignClient vehicleFeign,
                           BookingRepository bookingRepository,
                           CartRepository cartRepository,
                           CartItemRepository cartItemRepository) {
        this.vehicleFeign = vehicleFeign;
        this.bookingRepository = bookingRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartResponseDTO addToCart(CartItemRequestDTO request) {
        // 1. Extract userId from JWT / security context
        UUID userId = extractUserIdFromContext();

        // 2. Verify that the vehicle actually exists (feign call)
        VehicleResponseDTO vehicle = vehicleFeign.getVehicleById(request.getVehicleId());
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found: " + request.getVehicleId());
        }

        // 3. Check availability — returns false if already booked in that range
        boolean available = checkAvailability(
                request.getVehicleId(), request.getStartDate(), request.getEndDate());
        if (!available) {
            throw new IllegalStateException(
                    "Vehicle is not available between "
                    + request.getStartDate() + " and " + request.getEndDate());
        }

        // 4. Find existing cart for this user, or create a new one
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart(userId);
                    return cartRepository.save(newCart);
                });

        // 5. Build and save the CartItem
        CartItem item = new CartItem();
        item.setCart(cart);
        item.setVehicleId(request.getVehicleId());
        item.setStartDate(request.getStartDate());
        item.setEndDate(request.getEndDate());
        cartItemRepository.save(item);

        logger.info("Vehicle {} added to cart for user {}", request.getVehicleId(), userId);

        // 6. Return the updated cart as a response DTO
        return buildCartResponse(cart);
    }

    @Override
    public CartResponseDTO getCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No cart found for user: " + userId));
        return buildCartResponse(cart);
    }

    @Override
    public void removeFromCart(UUID cartItemId, UUID userId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found: " + cartItemId));
        if (!item.getCart().getUserId().equals(userId)) {
            throw new IllegalArgumentException("Cart item does not belong to this user");
        }
        cartItemRepository.delete(item);
        logger.info("Cart item {} removed for user {}", cartItemId, userId);
    }

    @Override
    public void clearCart(UUID userId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("No cart found for user: " + userId));
        List<CartItem> items = cartItemRepository.findByCart(cart);
        cartItemRepository.deleteAll(items);
        logger.info("Cart cleared for user {}", userId);
    }

    // ──────────────────────────────────────────────────
    // Private helpers
    // ──────────────────────────────────────────────────

    /**
     * Returns true if the vehicle has no overlapping confirmed bookings,
     * false if it is already booked in the requested date range.
     */
    private boolean checkAvailability(UUID vehicleId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Booking> overlapping = bookingRepository.findOverlappingBooking(vehicleId, startDate, endDate);
        if (!overlapping.isEmpty()) {
            logger.warn("Vehicle {} is already booked between {} and {}", vehicleId, startDate, endDate);
            return false;
        }
        return true;
    }

    /**
     * Extracts the authenticated user's UUID from the Spring Security context.
     * Assumes the JWT filter stores the userId (UUID string) as the principal name.
     */
    private UUID extractUserIdFromContext() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found in security context");
        }
        try {
            return UUID.fromString(auth.getName());
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Principal name is not a valid UUID: " + auth.getName(), e);
        }
    }

    /**
     * Maps a {@link Cart} and its {@link CartItem}s into a {@link CartResponseDTO}.
     */
    private CartResponseDTO buildCartResponse(Cart cart) {
        List<CartItemResponseDTO> itemDTOs = cartItemRepository.findByCart(cart)
                .stream()
                .map(i -> {
                    CartItemResponseDTO dto = new CartItemResponseDTO();
                    dto.setId(i.getId());
                    dto.setVehicleId(i.getVehicleId());
                    dto.setStartDate(i.getStartDate());
                    dto.setEndDate(i.getEndDate());
                    return dto;
                })
                .collect(Collectors.toList());

        CartResponseDTO response = new CartResponseDTO();
        response.setCartId(cart.getId());
        response.setUserId(cart.getUserId());
        response.setItems(itemDTOs);
        return response;
    }
}
