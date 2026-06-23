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



/* 
4771d141-cf6a-44fe-a24f-c9bea88ecce0 bookingId
order_T5C8eonDUM6nhp orderId
*/

