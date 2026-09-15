package com.booking_service.Repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking_service.Entity.Cart;

public interface CartRepository extends JpaRepository<Cart,UUID> {

    Optional<Cart> findByUserId(UUID userId);
}
