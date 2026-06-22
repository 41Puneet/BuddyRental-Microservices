package com.booking_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.booking_service.Controller.BookingController;
import com.booking_service.DTO.BookingRequestDTO;
import com.booking_service.DTO.BookingResponseDTO;
import com.booking_service.DTO.VehicleResponseDTO;
import com.booking_service.Enums.BookingStatus;
import com.booking_service.Service.BookingService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = BookingController.class,
        properties = {
                "spring.autoconfigure.exclude=" +
                        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
                        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
        })
class BookingApiControllerTests {

    private static final UUID USER_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");
    private static final UUID VEHICLE_ID = UUID.fromString("44444444-4444-4444-4444-444444444444");
    private static final UUID BOOKING_ID = UUID.fromString("55555555-5555-5555-5555-555555555555");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private BookingService bookingService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void createBooking_returnsCreated() throws Exception {
        when(bookingService.createBooking(any(), eq(USER_ID))).thenReturn(sampleBookingResponse());

        mockMvc.perform(post("/api/bookings")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer access-token")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBookingRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingId").value(BOOKING_ID.toString()))
                .andExpect(jsonPath("$.bookingStatus").value("PENDING"));
    }

    @Test
    void createBooking_unavailableVehicle_returnsConflict() throws Exception {
        when(bookingService.createBooking(any(), eq(USER_ID)))
                .thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "Vehicle is not available for the selected range"));

        mockMvc.perform(post("/api/bookings")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBookingRequest())))
                .andExpect(status().isConflict());
    }

    @Test
    void getBookingById_returnsBooking() throws Exception {
        when(bookingService.getBookingById(BOOKING_ID)).thenReturn(sampleBookingResponse());

        mockMvc.perform(get("/api/bookings/{bookingId}", BOOKING_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingId").value(BOOKING_ID.toString()));
    }

    @Test
    void updateBooking_returnsUpdatedBooking() throws Exception {
        when(bookingService.updateBooking(eq(BOOKING_ID), any())).thenReturn(sampleBookingResponse());

        mockMvc.perform(put("/api/bookings/update/{bookingId}", BOOKING_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBookingRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingStatus").value("PENDING"));
    }

    @Test
    void cancelBooking_returnsCancelledBooking() throws Exception {
        BookingResponseDTO cancelled = sampleBookingResponse();
        cancelled.setBookingStatus(BookingStatus.CANCELLED);
        when(bookingService.cancelBooking(BOOKING_ID)).thenReturn(cancelled);

        mockMvc.perform(delete("/api/bookings/{bookingId}", BOOKING_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingStatus").value("CANCELLED"));
    }

    @Test
    void getBookingsByUser_returnsPage() throws Exception {
        Page<BookingResponseDTO> page = new PageImpl<>(List.of(sampleBookingResponse()), PageRequest.of(0, 10), 1);
        when(bookingService.findByUserId(eq(USER_ID), any())).thenReturn(page);

        mockMvc.perform(get("/api/bookings/user/{userId}", USER_ID)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].bookingId").value(BOOKING_ID.toString()));
    }

    @Test
    void getBookingsByVehicle_returnsPage() throws Exception {
        Page<BookingResponseDTO> page = new PageImpl<>(List.of(sampleBookingResponse()), PageRequest.of(0, 10), 1);
        when(bookingService.findByVehicleId(eq(VEHICLE_ID), any())).thenReturn(page);

        mockMvc.perform(get("/api/bookings/vehicle/{vehicleId}", VEHICLE_ID)
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].bookingId").value(BOOKING_ID.toString()));
    }

    @Test
    void updateBookingStatus_returnsUpdatedStatus() throws Exception {
        BookingResponseDTO confirmed = sampleBookingResponse();
        confirmed.setBookingStatus(BookingStatus.CONFIRMED);
        when(bookingService.updateBookingStatus(eq(BOOKING_ID), eq(BookingStatus.CONFIRMED)))
                .thenReturn(confirmed);

        mockMvc.perform(put("/api/bookings/updateStatus/{bookingId}", BOOKING_ID)
                        .param("status", BookingStatus.CONFIRMED.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bookingStatus").value("CONFIRMED"));
    }

    @Test
    void invalidBookingPayload_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/bookings")
                        .header("X-User-Id", USER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void invalidUserIdHeader_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/bookings")
                        .header("X-User-Id", "not-a-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleBookingRequest())))
                .andExpect(status().isBadRequest());
    }

    private BookingRequestDTO sampleBookingRequest() {
        return new BookingRequestDTO(
                USER_ID,
                VEHICLE_ID,
                LocalDateTime.now().plusDays(2).withNano(0),
                LocalDateTime.now().plusDays(5).withNano(0));
    }

    private BookingResponseDTO sampleBookingResponse() {
        BookingResponseDTO response = new BookingResponseDTO();
        response.setBookingId(BOOKING_ID);
        response.setUserId(USER_ID);
        response.setStartDate(LocalDateTime.now().plusDays(2).withNano(0));
        response.setEndDate(LocalDateTime.now().plusDays(5).withNano(0));
        response.setTotalAmount(3600.0);
        response.setBookingStatus(BookingStatus.PENDING);
        VehicleResponseDTO vehicle = new VehicleResponseDTO();
        vehicle.setVehicleNumber("KL07AB1234");
        vehicle.setVehicleId(VEHICLE_ID);
        vehicle.setBrand("Toyota");
        vehicle.setModel("Innova");
        vehicle.setPricePerDay(1200.0);
        response.setVehicle(vehicle);
        return response;
    }
}
