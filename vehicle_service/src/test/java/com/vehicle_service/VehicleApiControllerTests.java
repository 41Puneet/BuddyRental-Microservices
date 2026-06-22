package com.vehicle_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicle_service.Controller.VehicleController;
import com.vehicle_service.DTO.VehicleRequestDTO;
import com.vehicle_service.DTO.VehicleResponseDTO;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;
import com.vehicle_service.Service.VehicleService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = VehicleController.class,
        properties = {
                "spring.autoconfigure.exclude=" +
                        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
                        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
        })
class VehicleApiControllerTests {

    private static final UUID OWNER_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void addVehicle_returnsCreated() throws Exception {
        VehicleResponseDTO response = sampleVehicleResponse();
        when(vehicleService.createVehicle(any(), eq(OWNER_ID))).thenReturn(response);

        mockMvc.perform(post("/api/vehicles/create")
                        .header(HttpHeaders.USER_AGENT, "PostmanRuntime/7.0")
                        .header("X-User-Id", OWNER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVehicleRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleNumber").value("KL07AB1234"))
                .andExpect(jsonPath("$.ownerId").value(OWNER_ID.toString()));
    }

    @Test
    void getVehicleByCity_returnsPage() throws Exception {
        Page<VehicleResponseDTO> page = new PageImpl<>(List.of(sampleVehicleResponse()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByCity("Bengaluru", PageRequest.of(0, 5))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles/city")
                        .param("city", "Bengaluru")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Toyota"));
    }

    @Test
    void getVehicleByTransmissionType_returnsList() throws Exception {
        when(vehicleService.findByTransmissionType(TransmissionType.AUTOMATIC))
                .thenReturn(List.of(sampleVehicleResponse()));

        mockMvc.perform(get("/api/vehicles/transmissionType")
                        .param("transmissionType", "AUTOMATIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicleNumber").value("KL07AB1234"));
    }

    @Test
    void getVehicleByFuelTypeAndVehicleType_returnsList() throws Exception {
        when(vehicleService.findByFuelTypeAndVehicleType(FuelType.PETROL, VehicleType.CAR))
                .thenReturn(List.of(sampleVehicleResponse()));

        mockMvc.perform(get("/api/vehicles/fuelType")
                        .param("fuelType", "PETROL")
                        .param("vehicleType", "CAR"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Bengaluru"));
    }

    @Test
    void getVehicleByFuelTypeAndTransmissionType_returnsList() throws Exception {
        when(vehicleService.findByFuelTypeAndTransmissionType(FuelType.PETROL, TransmissionType.AUTOMATIC))
                .thenReturn(List.of(sampleVehicleResponse()));

        mockMvc.perform(get("/api/vehicles/fuelTransmission")
                        .param("fuelType", "PETROL")
                        .param("transmissionType", "AUTOMATIC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pricePerDay").value(1200.0));
    }

    @Test
    void getVehicleByVehicleNumber_returnsVehicle() throws Exception {
        when(vehicleService.findByVehicleNumber("KL07AB1234"))
                .thenReturn(Optional.of(sampleVehicleResponse()));

        mockMvc.perform(get("/api/vehicles/vehicleNumber")
                        .param("vehicleNumber", "KL07AB1234"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value("KL07AB1234"));
    }

    @Test
    void getVehicleByVehicleNumber_returnsNotFound() throws Exception {
        when(vehicleService.findByVehicleNumber("MISSING-1234"))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vehicles/vehicleNumber")
                        .param("vehicleNumber", "MISSING-1234"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getVehicleByManufacturingYear_returnsPage() throws Exception {
        Page<VehicleResponseDTO> page = new PageImpl<>(List.of(sampleVehicleResponse()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByManufacturingYear(2023, PageRequest.of(0, 10))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles/manufacturingYear")
                        .param("manufacturingYear", "2023")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].manufacturingYear").value(2023));
    }

    @Test
    void getVehicleByBrand_returnsPage() throws Exception {
        Page<VehicleResponseDTO> page = new PageImpl<>(List.of(sampleVehicleResponse()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByBrand("Toyota", PageRequest.of(0, 10))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles/brand")
                        .param("brand", "Toyota")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].brand").value("Toyota"));
    }

    @Test
    void getVehicleByPriceBetween_returnsPage() throws Exception {
        Page<VehicleResponseDTO> page = new PageImpl<>(List.of(sampleVehicleResponse()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByPriceBetween(1000, 2000, PageRequest.of(0, 10))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles/priceBetween")
                        .param("minPrice", "1000")
                        .param("maxPrice", "2000")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].pricePerDay").value(1200.0));
    }

    @Test
    void getVehicleByModel_returnsPage() throws Exception {
        Page<VehicleResponseDTO> page = new PageImpl<>(List.of(sampleVehicleResponse()), PageRequest.of(0, 10), 1);
        when(vehicleService.findByModel("Innova", PageRequest.of(0, 10))).thenReturn(page);

        mockMvc.perform(get("/api/vehicles/model")
                        .param("model", "Innova")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].model").value("Innova"));
    }

    @Test
    void updateVehicle_returnsUpdatedVehicle() throws Exception {
        when(vehicleService.updateVehicle(any(), eq("KL07AB1234"), eq(OWNER_ID)))
                .thenReturn(sampleVehicleResponse());

        mockMvc.perform(put("/api/vehicles/update/{vehicleNumber}", "KL07AB1234")
                        .header("X-User-Id", OWNER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVehicleRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleNumber").value("KL07AB1234"));
    }

    @Test
    void deleteVehicle_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/vehicles/{vehicleNumber}", "KL07AB1234"))
                .andExpect(status().isNoContent());
    }

    @Test
    void invalidOwnerHeader_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/vehicles/create")
                        .header("X-User-Id", "not-a-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleVehicleRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void missingRequiredVehicleFields_returnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/vehicles/create")
                        .header("X-User-Id", OWNER_ID.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    private VehicleRequestDTO sampleVehicleRequest() {
        VehicleRequestDTO request = new VehicleRequestDTO();
        request.setVehicleNumber("KL07AB1234");
        request.setBrand("Toyota");
        request.setModel("Innova");
        request.setCity("Bengaluru");
        request.setVehicleType(VehicleType.CAR);
        request.setFuelType(FuelType.PETROL);
        request.setTransmissionType(TransmissionType.AUTOMATIC);
        request.setPricePerDay(1200.0);
        request.setSecurityPrice(5000.0);
        request.setAdvancePayment(1000.0);
        request.setManufacturingYear(2023);
        return request;
    }

    private VehicleResponseDTO sampleVehicleResponse() {
        VehicleResponseDTO response = new VehicleResponseDTO();
        response.setVehicleNumber("KL07AB1234");
        response.setOwnerId(OWNER_ID);
        response.setBrand("Toyota");
        response.setModel("Innova");
        response.setCity("Bengaluru");
        response.setVehicleType(VehicleType.CAR);
        response.setFuelType(FuelType.PETROL);
        response.setTransmissionType(TransmissionType.AUTOMATIC);
        response.setPricePerDay(1200.0);
        response.setSecurityPrice(5000.0);
        response.setAdvancePayment(1000.0);
        response.setManufacturingYear(2023);
        response.setIsAvailable(Boolean.TRUE);
        return response;
    }
}
