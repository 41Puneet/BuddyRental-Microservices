package com.booking_service.DTO;

import java.util.List;
import java.util.UUID;

public class CartResponseDTO {
    private UUID cartId;
    private UUID userId;
    private List<CartItemResponseDTO> items;
    public UUID getCartId() {
        return cartId;
    }
    public void setCartId(UUID cartId) {
        this.cartId = cartId;
    }
    public UUID getUserId() {
        return userId;
    }
    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    public List<CartItemResponseDTO> getItems() {
        return items;
    }
    public void setItems(List<CartItemResponseDTO> items) {
        this.items = items;
    }

    
}
