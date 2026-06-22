package com.payment_service.Service;


import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.payment_service.DTO.PaymentRequestVerificationDTO;
import com.payment_service.DTO.PaymentRequestDTO;
import com.payment_service.DTO.PaymentResponseDTO;
import com.payment_service.DTO.RazorpayOrderResponseDTO;
import com.razorpay.RazorpayException;

public interface PaymentService {

    PaymentResponseDTO getPaymentById(UUID paymentId);

    PaymentResponseDTO getPaymentByBookingId(UUID bookingId);

    Page<PaymentResponseDTO> findPaymentByUserId(UUID userId,Pageable pageable);

    PaymentResponseDTO findPaymentByTransactionId(String transactionId);
    
    RazorpayOrderResponseDTO createOrder(UUID bookingId, UUID userId) throws RazorpayException;
    
    PaymentResponseDTO verifyPayment(PaymentRequestVerificationDTO dto, UUID userId);
}
