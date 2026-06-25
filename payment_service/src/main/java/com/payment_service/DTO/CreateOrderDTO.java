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

pay_T5CM5iMIl7bT2B transactionId

"paymentId": "e67c6cd6-62c7-4418-b60d-7168c51887af",
    "bookingId": "4771d141-cf6a-44fe-a24f-c9bea88ecce0",
    "userId": "f422209e-243d-4612-92fd-a686b8317b55",
    "transactionId": "pay_T5CM5iMIl7bT2B",
    "amount": 1500.0,
    "paymentStatus": "SUCCESS",
    "createdAt": null,
    "booking": null
*/

