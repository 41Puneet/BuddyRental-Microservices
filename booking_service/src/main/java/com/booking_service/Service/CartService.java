package com.booking_service.Service;

import java.util.UUID;

import com.booking_service.DTO.CartItemRequestDTO;
import com.booking_service.DTO.CartResponseDTO;

public interface CartService {
    

    CartResponseDTO addToCart(CartItemRequestDTO request);

    CartResponseDTO getCart(UUID userId);

    void removeFromCart(UUID cartItemId,UUID userId);

    void clearCart(UUID userID);
}
