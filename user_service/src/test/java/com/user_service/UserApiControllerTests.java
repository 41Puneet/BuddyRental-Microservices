package com.user_service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user_service.Controller.AuthController;
import com.user_service.Controller.UserController;
import com.user_service.DTO.AuthResponseDTO;
import com.user_service.DTO.LoginRequestDTO;
import com.user_service.DTO.RegisterRequestDTO;
import com.user_service.DTO.UserDTO;
import com.user_service.Enums.Role;
import com.user_service.Service.AuthService;
import com.user_service.Service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest(
        classes = {UserController.class, AuthController.class, GlobalExceptionHandler.class},
        properties = {
                "spring.autoconfigure.exclude=" +
                        "org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration," +
                        "org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration"
        })
class UserApiControllerTests {

    private static final String ACCESS_TOKEN = "access-token";
    private static final String REFRESH_TOKEN = "refresh-token";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void registerUser_returnsTokens() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO(
                "Aarav Sharma",
                "aarav@example.com",
                "+919876543210",
                "Password123",
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now());

        whenAuthRegisterReturnsTokens();

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void loginUser_returnsTokens() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO("aarav@example.com", "Password123");

        whenAuthLoginReturnsTokens();

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN))
                .andExpect(jsonPath("$.tokenType").value("Bearer"));
    }

    @Test
    void refreshToken_returnsNewAccessToken() throws Exception {
        when(authService.refreshToken(REFRESH_TOKEN))
                .thenReturn(new AuthResponseDTO(ACCESS_TOKEN, REFRESH_TOKEN));

        mockMvc.perform(post("/api/auth/refresh-token")
                        .param("refreshToken", REFRESH_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(ACCESS_TOKEN))
                .andExpect(jsonPath("$.refreshToken").value(REFRESH_TOKEN));
    }

    @Test
    void logout_returnsOk() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + REFRESH_TOKEN + "\""))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    void createUser_returnsUserDto() throws Exception {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UserDTO userDto = sampleUserDto(userId);

        when(userService.createUser(any())).thenReturn(userDto);

        mockMvc.perform(post("/api/users/create")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleRegisterRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.email").value("aarav@example.com"));
    }

    @Test
    void createUserWithMissingFields_returnsBadRequest() throws Exception {
        when(userService.createUser(any()))
                .thenThrow(new IllegalArgumentException("full name is required"));

        mockMvc.perform(post("/api/users/create")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("full name is required"));
    }

    @Test
    void getUserByEmail_returnsUserDto() throws Exception {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UserDTO userDto = sampleUserDto(userId);

        when(userService.getUserByEmail("aarav@example.com"))
                .thenReturn(Optional.of(userDto));

        mockMvc.perform(get("/api/users/email/{email}", "aarav@example.com")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.phoneNumber").value("+919876543210"));
    }

    @Test
    void getUserByPhone_returnsUserDto() throws Exception {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UserDTO userDto = sampleUserDto(userId);

        when(userService.getUserByPhoneNumber("+919876543210"))
                .thenReturn(Optional.of(userDto));

        mockMvc.perform(get("/api/users/phone")
                        .param("phoneNumber", "+919876543210")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.email").value("aarav@example.com"));
    }

    @Test
    void updateUser_returnsUpdatedUser() throws Exception {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");
        UserDTO userDto = sampleUserDto(userId);
        userDto.setFullName("Aarav Kumar");

        when(userService.updateUser(eq(userId), any())).thenReturn(userDto);

        mockMvc.perform(put("/api/users/update/{id}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Aarav Kumar"));
    }

    @Test
    void deleteUser_returnsNoContent() throws Exception {
        UUID userId = UUID.fromString("11111111-1111-1111-1111-111111111111");

        mockMvc.perform(delete("/api/users/delete/{id}", userId)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN))
                .andExpect(status().isNoContent());
    }

    @Test
    void invalidLogin_returnsBadRequest() throws Exception {
        when(authService.login(any()))
                .thenThrow(new IllegalArgumentException("User not found with email: missing@example.com"));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new LoginRequestDTO("missing@example.com", "badpass"))))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found with email: missing@example.com"));
    }

    @Test
    void registerMissingFields_returnsBadRequest() throws Exception {
        when(authService.register(any()))
                .thenThrow(new IllegalArgumentException("email is required"));

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("email is required"));
    }

    private void whenAuthRegisterReturnsTokens() {
        when(authService.register(any()))
                .thenReturn(new AuthResponseDTO(ACCESS_TOKEN, REFRESH_TOKEN));
    }

    private void whenAuthLoginReturnsTokens() {
        when(authService.login(any()))
                .thenReturn(new AuthResponseDTO(ACCESS_TOKEN, REFRESH_TOKEN));
    }

    private RegisterRequestDTO sampleRegisterRequest() {
        return new RegisterRequestDTO(
                "Aarav Sharma",
                "aarav@example.com",
                "+919876543210",
                "Password123",
                Role.USER,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    private UserDTO sampleUserDto(UUID userId) {
        UserDTO userDto = new UserDTO();
        userDto.setId(userId);
        userDto.setFullName("Aarav Sharma");
        userDto.setEmail("aarav@example.com");
        userDto.setPhoneNumber("+919876543210");
        userDto.setRole(Role.USER);
        userDto.setProfilePicture("https://cdn.example.com/profile/aarav.png");
        userDto.setIsVerified(Boolean.TRUE);
        userDto.setRating(4.9);
        userDto.setCreatedAt(LocalDateTime.now().minusDays(1));
        userDto.setUpdatedAt(LocalDateTime.now());
        return userDto;
    }
}
