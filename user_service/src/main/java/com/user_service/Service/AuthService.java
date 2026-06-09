package com.user_service.Service;
import com.user_service.DTO.AuthResponseDTO;
import com.user_service.DTO.LoginRequestDTO;
import com.user_service.DTO.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO registerRequestDTO);
    AuthResponseDTO login(LoginRequestDTO loginRequestDTO);
    AuthResponseDTO refreshToken(String refreshToken);
    void logout(String refreshToken);
}
