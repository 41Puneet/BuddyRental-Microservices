package com.payment_service.PaymentRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import com.payment_service.Entity.Payment;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,UUID> {
    
Optional<Payment> findByTransactionId(String transactionId);

Optional<Payment> findByBookingId(UUID bookingId);

Page<Payment> findByUserId(UUID userId, Pageable pageable);
}
