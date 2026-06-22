package com.payment_service.DTO;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;


public class PaymentRequestDTO {
    @NotBlank
    private UUID bookingId;

public PaymentRequestDTO(){

}
public PaymentRequestDTO(UUID bookingId){
    this.bookingId=bookingId;
}
public UUID getBookingId() {
    return bookingId;
}
public void setBookingId(UUID bookingId) {
    this.bookingId = bookingId;
}

}
