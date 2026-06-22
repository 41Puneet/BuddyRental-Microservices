package com.payment_service.DTO;

import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public class PaymentRequestVerificationDTO {
    @NotNull
    private UUID bookingId;
    @NotBlank
    private String razorpayPaymentId;
    @NotBlank
    private String razorpayOrderId;
    @NotBlank
    private String razorpaySignature;


    public PaymentRequestVerificationDTO(){

    }
    public PaymentRequestVerificationDTO(UUID bookingId,String razorpayPaymentId,String razorpayOrderId,String razorpaySignature){
        this.bookingId=bookingId;
        this.razorpayPaymentId=razorpayPaymentId;
        this.razorpayOrderId=razorpayOrderId;
        this.razorpaySignature=razorpaySignature;
    }
    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }
}
