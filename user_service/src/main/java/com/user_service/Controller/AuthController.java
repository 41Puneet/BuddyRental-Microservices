package com.user_service.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.DTO.AuthResponseDTO;
import com.user_service.DTO.LoginRequestDTO;
import com.user_service.DTO.RegisterRequestDTO;
import com.user_service.Service.AuthService;



@RestController
@RequestMapping("/api/auth")
public class AuthController {
    

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }
     @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO registerRequestDTO){
        return authService.register(registerRequestDTO);
    }
    @PostMapping("/login")
    public AuthResponseDTO login(
        @RequestBody LoginRequestDTO loginRequestDTO){
        return authService.login(loginRequestDTO);
    }
    @PostMapping("/refresh-token")
    public AuthResponseDTO refreshToken(@RequestParam String refreshToken){
        return authService.refreshToken(refreshToken);
    }
    @PostMapping("/logout")
    public void logout(@RequestBody String refreshToken){
        authService.logout(refreshToken);
    }
}
