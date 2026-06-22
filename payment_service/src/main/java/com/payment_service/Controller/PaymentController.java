package com.payment_service.Controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment_service.DTO.CreateOrderDTO;
import com.payment_service.DTO.PaymentRequestDTO;
import com.payment_service.DTO.PaymentResponseDTO;
import com.payment_service.DTO.RazorpayOrderResponseDTO;
import com.payment_service.Service.PaymentService;
import com.razorpay.RazorpayException;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @Valid @RequestBody PaymentRequestDTO paymentRequestDTO,
            @RequestParam("userId") UUID userId) {
        return ResponseEntity.ok(paymentService.createPayment(paymentRequestDTO, userId));
    }

    @PostMapping("/create-order")
    public ResponseEntity<RazorpayOrderResponseDTO> createOrder(@Valid @RequestBody CreateOrderDTO createOrderDTO,UUID userId) throws RazorpayException {
        return ResponseEntity.ok(paymentService.createOrder(createOrderDTO.getBookingId(), userId));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable UUID paymentId) {
        return ResponseEntity.ok(paymentService.getPaymentById(paymentId));
    }

    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByBookingId(@PathVariable UUID bookingId) {
        return ResponseEntity.ok(paymentService.getPaymentByBookingId(bookingId));
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<PaymentResponseDTO> getPaymentByTransactionId(@PathVariable String transactionId) {
        return ResponseEntity.ok(paymentService.findPaymentByTransactionId(transactionId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPaymentsByUserId(
            @PathVariable UUID userId,
            Pageable pageable) {
        return ResponseEntity.ok(paymentService.findPaymentByUserId(userId, pageable));
    }
}
