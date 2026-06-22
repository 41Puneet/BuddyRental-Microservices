# BuddyRental API Testing Suite

This repo now includes:

- A Postman collection for the public controller surface of the four business microservices
- A matching Postman environment with localhost ports and sample IDs
- Spring Boot MockMvc controller tests for user, vehicle, booking, and payment services

## Discovered Controller Endpoints

### User Service

- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh-token?refreshToken=...`
- `POST /api/auth/logout`
- `POST /api/users/create`
- `GET /api/users/email/{email}`
- `GET /api/users/phone?phoneNumber=...`
- `PUT /api/users/update/{id}`
- `DELETE /api/users/delete/{id}`

### Vehicle Service

- `POST /api/vehicles/create`
- `GET /api/vehicles/city`
- `GET /api/vehicles/transmissionType`
- `GET /api/vehicles/fuelType`
- `GET /api/vehicles/fuelTransmission`
- `GET /api/vehicles/vehicleNumber`
- `GET /api/vehicles/manufacturingYear`
- `GET /api/vehicles/brand`
- `GET /api/vehicles/priceBetween`
- `GET /api/vehicles/model`
- `PUT /api/vehicles/update/{vehicleNumber}`
- `DELETE /api/vehicles/{vehicleNumber}`

### Booking Service

- `POST /api/bookings`
- `PUT /api/bookings/update/{bookingId}`
- `DELETE /api/bookings/{bookingId}`
- `GET /api/bookings/{bookingId}`
- `GET /api/bookings/user/{userId}`
- `GET /api/bookings/vehicle/{vehicleId}`
- `PATCH /api/bookings/updateStatus/{bookingId}`

### Payment Service

- `POST /api/payments/verifyPayment`
- `POST /api/payments/create-order`
- `GET /api/payments/{paymentId}`
- `GET /api/payments/booking/{bookingId}`
- `GET /api/payments/transaction/{transactionId}`
- `GET /api/payments/user/{userId}`

## Important Contract Notes

- `user_service` protects `/api/users/**` and `POST /api/auth/logout` with JWT auth.
- `vehicle_service`, `booking_service`, and `payment_service` use `X-User-Id` headers on write operations and some read operations.
- The codebase has a contract mismatch: `booking_service` calls `GET /api/vehicles/{vehicleId}` through a Feign client, but the public vehicle controller only exposes `vehicleNumber` and search endpoints. The Postman flow includes a sample `vehicle_id`, but a true live end-to-end booking flow will need either a seeded vehicle UUID or an additional vehicle-by-id endpoint aligned with the Feign client.

## Recommended Execution Flow

1. `Register User`
1. `Login User`
1. `Add Vehicle`
1. `Create Booking`
1. `Create Order`
1. `Verify Payment`
1. Assert booking status is `CONFIRMED`

The collection saves:

- `access_token`
- `refresh_token`
- `user_id`
- `vehicle_id`
- `booking_id`
- `payment_id`
- `transaction_id`

## Sample Payloads

### Register User

```json
{
  "fullName": "Aarav Sharma",
  "email": "aarav@example.com",
  "phoneNumber": "+919876543210",
  "password": "Password123",
  "role": "USER"
}
```

### Add Vehicle

```json
{
  "vehicleNumber": "KL07AB1234",
  "brand": "Toyota",
  "model": "Innova",
  "city": "Bengaluru",
  "vehicleType": "CAR",
  "fuelType": "PETROL",
  "transmissionType": "AUTOMATIC",
  "pricePerDay": 1200,
  "securityPrice": 5000,
  "advancePayment": 1000,
  "manufacturingYear": 2023
}
```

### Create Booking

```json
{
  "userId": "11111111-1111-1111-1111-111111111111",
  "vehicleId": "44444444-4444-4444-4444-444444444444",
  "startDate": "2026-06-25T10:00:00",
  "endDate": "2026-06-28T10:00:00"
}
```

### Verify Payment

```json
{
  "bookingId": "55555555-5555-5555-5555-555555555555",
  "razorpayPaymentId": "pay_test_123",
  "razorpayOrderId": "order_test_123",
  "razorpaySignature": "signature_test_123"
}
```

## Generated Test Classes

- [`user_service/src/test/java/com/user_service/UserApiControllerTests.java`](../user_service/src/test/java/com/user_service/UserApiControllerTests.java)
- [`vehicle_service/src/test/java/com/vehicle_service/VehicleApiControllerTests.java`](../vehicle_service/src/test/java/com/vehicle_service/VehicleApiControllerTests.java)
- [`booking_service/src/test/java/com/booking_service/BookingApiControllerTests.java`](../booking_service/src/test/java/com/booking_service/BookingApiControllerTests.java)
- [`payment_service/src/test/java/com/payment_service/PaymentApiControllerTests.java`](../payment_service/src/test/java/com/payment_service/PaymentApiControllerTests.java)

## Gaps Worth Fixing Later

- Expose a vehicle-by-id controller endpoint or update the booking Feign client to use an endpoint that actually exists.
- Add a shared exception handler in booking, vehicle, and payment services if you want consistent `400`/`404` translation for service-layer `IllegalArgumentException`s.
