package com.payment_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment_service.Controller.PaymentController;
import com.payment_service.DTO.BookingResponseDTO;
import com.payment_service.DTO.CreateOrderDTO;
import com.payment_service.DTO.PaymentRequestVerificationDTO;
import com.payment_service.DTO.PaymentResponseDTO;
import com.payment_service.DTO.RazorpayOrderResponseDTO;
import com.payment_service.Enum.PaymentStatus;
import com.payment_service.Service.PaymentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = PaymentController.class,
        properties = {
                "spring.autoconfigure.exclude=" +
                        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
                        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
        })
class PaymentApiControllerTests {

    private static final UUID USER_ID = UUID.fromString("66666666-6666-6666-6666-666666666666");
    private static final UUID BOOKING_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");
    private static final UUID PAYMENT_ID = UUID.fromString("77777777-7777-7777-7777-777777777777");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createOrder_returnsRazorpayOrder() throws Exception {
        when(paymentService.createOrder(eq(BOOKING_ID), eq(USER_ID)))
                .thenReturn(new RazorpayOrderResponseDTO("order_test_123", 3600.0, "INR"));

        mockMvc.perform(post("/api/payments/create-order")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateOrderDTO(BOOKING_ID))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId").value("order_test_123"))
                .andExpect(jsonPath("$.currency").value("INR"));
    }

    @Test
    void verifyPayment_returnsPaymentResponse() throws Exception {
        when(paymentService.verifyPayment(any(), eq(USER_ID))).thenReturn(samplePaymentResponse());

        mockMvc.perform(post("/api/payments/verifyPayment")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVerificationRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(PAYMENT_ID.toString()))
                .andExpect(jsonPath("$.paymentStatus").value("SUCCESS"));
    }

    @Test
    void verifyPayment_alreadyExists_returnsConflict() throws Exception {
        when(paymentService.verifyPayment(any(), eq(USER_ID)))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "payment is already made"));

        mockMvc.perform(post("/api/payments/verifyPayment")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVerificationRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    void getPaymentById_returnsPayment() throws Exception {
        when(paymentService.getPaymentById(PAYMENT_ID)).thenReturn(samplePaymentResponse());

        mockMvc.perform(get("/api/payments/{paymentId}", PAYMENT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(PAYMENT_ID.toString()));
    }

    @Test
    void getPaymentByBookingId_returnsPayment() throws Exception {
        when(paymentService.getPaymentByBookingId(BOOKING_ID)).thenReturn(samplePaymentResponse());

        mockMvc.perform(get("/api/payments/booking/{bookingId}", BOOKING_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(BOOKING_ID.toString()));
    }

    @Test
    void getPaymentByTransactionId_returnsPayment() throws Exception {
        when(paymentService.findPaymentByTransactionId("txn_123456789"))
                .thenReturn(samplePaymentResponse());

        mockMvc.perform(get("/api/payments/transaction/{transactionId}", "txn_123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactionId").value("txn_123456789"));
    }

    @Test
    void getPaymentsByUserId_returnsPage() throws Exception {
        Page<PaymentResponseDTO> page = new PageImpl<>(List.of(samplePaymentResponse()), PageRequest.of(0, 10), 1);
        when(paymentService.findPaymentByUserId(eq(USER_ID), any())).thenReturn(page);

        mockMvc.perform(get("/api/payments/user/{userId}", USER_ID)
                        .header("X-User-Id", USER_ID.toString())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].paymentId").value(PAYMENT_ID.toString()));
    }

    @Test
    void missingRequiredFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/payments/verifyPayment")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidUserIdHeader_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/payments/create-order")
                        .header("X-User-Id", "not-a-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new CreateOrderDTO(BOOKING_ID))))
                .andExpect(status().isBadRequest());
    }

    private PaymentRequestVerificationDTO sampleVerificationRequest() {
        return new PaymentRequestVerificationDTO(
                BOOKING_ID,
                "pay_test_123",
                "order_test_123",
                "signature_test_123");
    }

    private PaymentResponseDTO samplePaymentResponse() {
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setPaymentId(PAYMENT_ID);
        response.setBookingId(BOOKING_ID);
        response.setUserId(USER_ID);
        response.setTransactionId("pay_test_123");
        response.setAmount(3600.0);
        response.setPaymentStatus(PaymentStatus.SUCCESS);
        response.setCreatedAt(LocalDateTime.now());
        BookingResponseDTO booking = new BookingResponseDTO();
        booking.setBookingId(BOOKING_ID);
        booking.setUserId(USER_ID);
        booking.setStartDate(LocalDateTime.now().plusDays(2));
        booking.setEndDate(LocalDateTime.now().plusDays(5));
        booking.setTotalAmount(3600.0);
        response.setBooking(booking);
        return response;
    }
}
