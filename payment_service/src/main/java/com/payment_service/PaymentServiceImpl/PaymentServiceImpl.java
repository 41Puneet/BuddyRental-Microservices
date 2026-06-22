package com.payment_service.PaymentServiceImpl;


import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.payment_service.BookingFeignClient.BookingFeignClient;
import com.payment_service.DTO.BookingResponseDTO;
import com.payment_service.DTO.PaymentRequestDTO;
import com.payment_service.DTO.PaymentResponseDTO;
import com.payment_service.DTO.RazorpayOrderResponseDTO;
import com.payment_service.Entity.Payment;
import com.payment_service.PaymentRepository.PaymentRepository;
import com.payment_service.Service.PaymentService;
import com.razorpay.RazorpayException;

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
    public RazorpayOrderResponseDTO createOrder(UUID bookingId, UUID userId) throws RazorpayException {
       
        return null;
    }

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO paymentRequestDTO, UUID userId) {
        Optional<Payment> payment=paymentRepository.findByBookingId(paymentRequestDTO.getBookingId());
        if(payment.isPresent()){
            logger.warn("Payment already made with this bookingId{}",paymentRequestDTO.getBookingId());
            throw new IllegalArgumentException("Payment is already made with this bookingId"+paymentRequestDTO.getBookingId());
        }
        BookingResponseDTO booking=bookingFeignClient.getBookingById(paymentRequestDTO.getBookingId());
        Payment newPayment =new Payment();
        newPayment.setBookingId(booking.getBookingId());
        newPayment.setUserId(userId);
        newPayment.setTransactionId(UUID.randomUUID().toString());
        newPayment.setAmount(booking.getTotalAmount());
        return null;
    }
private PaymentResponseDTO mapToPaymentDTO(Payment payment){
    PaymentResponseDTO response=new PaymentResponseDTO();
    response.setPaymentId(payment.getPaymentId());
    response.setBookingId(payment.getBookingId());
    response.setUserId(payment.getUserId());
    response.setTransactionId(payment.getTransactionId());
    response.setAmount(payment.getAmount());
    response.setPaymentStatus(payment.getPaymentStatus());
    return response;
}
    @Override
    public PaymentResponseDTO findPaymentByTransactionId(String transactionId) {
        Optional<Payment>payment=paymentRepository.findByTransactionId(transactionId);
        logger.info("Payment with this transactionId{} ",transactionId);
        return payment.map(this::mapToPaymentDTO).orElseThrow(() -> {
            logger.warn("Payment not found with this transactionId{}",transactionId);
            return new IllegalArgumentException("Payment not found with this transactionId"+transactionId);
        });
    }

    @Override
    public Page<PaymentResponseDTO> findPaymentByUserId(UUID userId, Pageable pageable) {
       Page<Payment>payment=paymentRepository.findByUserId(userId, pageable);
       return payment.map(this::mapToPaymentDTO);
    }

    @Override
    public PaymentResponseDTO getPaymentByBookingId(UUID bookingId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PaymentResponseDTO getPaymentById(UUID paymentId) {
        // TODO Auto-generated method stub
        return null;
    }
    

}