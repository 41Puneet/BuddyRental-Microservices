package com.booking_service.Repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking_service.Entity.Cart;
import com.booking_service.Entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {

    List<CartItem> findByCart(Cart cart);
}
