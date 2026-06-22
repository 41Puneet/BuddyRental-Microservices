package com.payment_service.PaymentServiceImpl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.payment_service.PaymentRepository.PaymentRepository;
import com.payment_service.BookingFeignClient.BookingFeignClient;
import com.payment_service.DTO.PaymentRequestDTO;
import com.payment_service.DTO.PaymentResponseDTO;
import com.payment_service.DTO.BookingResponseDTO;
import com.payment_service.Entity.Payment;
import com.payment_service.Enum.BookingStatus;
import com.payment_service.Enum.PaymentStatus;
import com.payment_service.Service.PaymentService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingFeignClient bookingFeignClient;
    private final Logger logger=LoggerFactory.getLogger(PaymentServiceImpl.class);
    
    
    public PaymentServiceImpl(PaymentRepository paymentRepository,BookingFeignClient bookingFeignClient){
        this.paymentRepository=paymentRepository;
        this.bookingFeignClient=bookingFeignClient;
        
    }


    @Override
public PaymentResponseDTO createPayment(
        PaymentRequestDTO paymentRequestDTO,
        UUID userId) {

    paymentRepository.findByBookingId(
            paymentRequestDTO.getBookingId())
        .ifPresent(p -> {
            throw new IllegalArgumentException(
                    "Payment already exists for this booking");
        });

    BookingResponseDTO booking =
            bookingFeignClient.getBookingById(
                    paymentRequestDTO.getBookingId());

    if (booking.getStatus() == BookingStatus.CANCELLED) {
        throw new IllegalArgumentException(
                "Cannot make payment for cancelled booking");
    }

    Payment payment = new Payment();
    payment.setBookingId(booking.getBookingId());
    payment.setUserId(userId);
    payment.setTransactionId(UUID.randomUUID().toString());
    payment.setAmount(booking.getTotalAmount());
    payment.setPaymentStatus(PaymentStatus.SUCCESS);

    Payment saved = paymentRepository.save(payment);

    bookingFeignClient.updateBookingStatus(
            booking.getBookingId(),
            BookingStatus.CONFIRMED);

    logger.info(
            "Payment created successfully for bookingId {}",
            booking.getBookingId());
    return mapToPaymentResponse(saved);
}
    private PaymentResponseDTO mapToPaymentResponse(Payment payment){
        BookingResponseDTO booking=bookingFeignClient.getBookingById(payment.getBookingId());
        PaymentResponseDTO dto=new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        dto.setBookingId(payment.getBookingId());
        dto.setUserId(payment.getUserId());
        dto.setTransactionId(payment.getTransactionId());
        dto.setAmount(payment.getAmount());
        dto.setPaymentStatus(payment.getPaymentStatus());
        dto.setBooking(booking);
        return dto;
    }

    @Override
    public PaymentResponseDTO findPaymentByTransactionId(String transactionId) {
        Payment payment = paymentRepository.findByTransactionId(transactionId)
            .orElseThrow(() -> {
                logger.warn("Payment not found with this transactionId {}", transactionId);
                return new IllegalArgumentException("payment not found with this transaction id:" + transactionId);
            });
        return mapToPaymentResponse(payment);
    }

    @Override
    public Page<PaymentResponseDTO> findPaymentByUserId(UUID userId, Pageable pageable) {
        Page<Payment>payment=paymentRepository.findByUserId(userId,pageable);
        logger.info("List of payment made by the user{} for booking ",userId);
        return payment.map(this::mapToPaymentResponse);
        
    }

    @Override
    public PaymentResponseDTO getPaymentByBookingId(UUID bookingId) {
      Optional<Payment> payment = paymentRepository.findByBookingId(bookingId);
      return payment.map(this::mapToPaymentResponse)
          .orElseThrow(() -> {
              logger.warn("Payment not found with bookingId {}", bookingId);
              return new IllegalArgumentException("payment not found with booking id:" + bookingId);
          });
    }

    @Override
    public PaymentResponseDTO getPaymentById(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> {
                logger.warn("Payment not found with paymentId {}", paymentId);
                return new IllegalArgumentException("payment not found with payment id:" + paymentId);
            });
        return mapToPaymentResponse(payment);
    }
    
}
