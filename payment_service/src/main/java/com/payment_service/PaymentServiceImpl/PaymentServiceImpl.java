package com.payment_service.PaymentServiceImpl;

import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.payment_service.BookingFeignClient.BookingFeignClient;
import com.payment_service.DTO.BookingResponseDTO;
import com.payment_service.DTO.PaymentRequestVerificationDTO;
import com.payment_service.DTO.PaymentResponseDTO;
import com.payment_service.DTO.RazorpayOrderResponseDTO;
import com.payment_service.Entity.Payment;
import com.payment_service.Enum.BookingStatus;
import com.payment_service.Enum.PaymentStatus;
import com.payment_service.PaymentRepository.PaymentRepository;
import com.payment_service.Service.PaymentService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
 
@Value("${razorpay.key.secret}")
private String razorpaySecret;
    private final PaymentRepository paymentRepository;
    private final BookingFeignClient bookingFeignClient;
    private final RazorpayClient razorpayClient;
    private final Logger logger=LoggerFactory.getLogger(PaymentServiceImpl.class);
    public PaymentServiceImpl(PaymentRepository paymentRepository,BookingFeignClient bookingFeignClient,RazorpayClient razorpayClient){
        this.paymentRepository=paymentRepository;
        this.bookingFeignClient=bookingFeignClient;
        this.razorpayClient=razorpayClient;
    }

    @Override
    public RazorpayOrderResponseDTO createOrder(UUID bookingId, UUID userId) throws RazorpayException {
       BookingResponseDTO booking=bookingFeignClient.getBookingById(bookingId);
       JSONObject object=new JSONObject();
       object.put("amount", booking.getTotalAmount()*100);
       object.put("currency", "INR");
       object.put("receipt",bookingId.toString());

       Order order;
try {
    order=razorpayClient.orders.create(object);
} catch (RazorpayException e) {
logger.error("Failed to create Razorpay order", e);
    throw new RuntimeException("Unable to create payment order");
}

    RazorpayOrderResponseDTO response = new RazorpayOrderResponseDTO();

        response.setOrderId(order.get("id"));
        response.setAmount(booking.getTotalAmount());
        response.setCurrency("INR");
        return response;
    
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
       Optional<Payment>payment=paymentRepository.findByBookingId(bookingId);
       return payment.map(this::mapToPaymentDTO).orElseThrow(()->{
        logger.warn("Payment not found with this bookingId{}",bookingId);
        return new IllegalArgumentException("Payment not found with this bookingId");
       });
        
    }

    @Override
    public PaymentResponseDTO getPaymentById(UUID paymentId) {
       Optional<Payment>payment=paymentRepository.findById(paymentId);
       return payment.map(this::mapToPaymentDTO).orElseThrow(()->{
        logger.warn("Payment not found with this paymentId{}",paymentId);
        return new IllegalArgumentException("Payment not found with this bookingId");
       });
    }
    @Override
   public PaymentResponseDTO verifyPayment(PaymentRequestVerificationDTO dto,UUID userId){
Optional<Payment>checkpayment=paymentRepository.findByBookingId(dto.getBookingId());
if(checkpayment.isPresent()){
    throw new IllegalArgumentException("payment is already made for this bookingId"+dto.getBookingId());
}
BookingResponseDTO booking=bookingFeignClient.getBookingById(dto.getBookingId());
Payment payment=new Payment();
payment.setBookingId(booking.getBookingId());
payment.setUserId(userId);
payment.setAmount(booking.getTotalAmount());
payment.setTransactionId(dto.getRazorpayPaymentId());
String getGeneratedSignatureData=dto.getRazorpayOrderId()+"|"+dto.getRazorpayPaymentId();
boolean valid;
try {
    valid = Utils.verifySignature(getGeneratedSignatureData, dto.getRazorpaySignature(), razorpaySecret);
} catch (RazorpayException e) {
    logger.error("Failed to verify Razorpay signature", e);
    throw new IllegalArgumentException("Invalid payment signature");
}
if (!valid) {
    throw new IllegalArgumentException("Invalid payment signature");
}
payment.setPaymentStatus(PaymentStatus.SUCCESS);
Payment saved=paymentRepository.save(payment);
bookingFeignClient.updateBookingStatus(booking.getBookingId(), BookingStatus.CONFIRMED);
return mapToPaymentDTO(saved);
   }

}