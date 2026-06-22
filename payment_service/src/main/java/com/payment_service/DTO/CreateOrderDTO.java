package com.payment_service.DTO;
import java.util.UUID;

public class CreateOrderDTO {
   
    
    private UUID bookingId;

    public CreateOrderDTO(){

    }
    public CreateOrderDTO(UUID bookingId){
        this.bookingId=bookingId;
    }
    public UUID getBookingId() {
        return bookingId;
    }
    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }
    
}
