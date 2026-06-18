package com.user_service.ServiceImpl;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user_service.DTO.AuthResponseDTO;
import com.user_service.DTO.LoginRequestDTO;
import com.user_service.DTO.RegisterRequestDTO;
import com.user_service.DTO.UserDTO;
import com.user_service.Entites.RefreshToken;
import com.user_service.Entites.User;
import com.user_service.Repository.RefreshTokenRepository;
import com.user_service.Repository.UserRepository;
import com.user_service.Security.CustomUserDetailsService;
import com.user_service.Security.JwtService;
import com.user_service.Service.AuthService;
import com.user_service.Service.UserService;


@Service
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CustomUserDetailsService CustomUserDetailsService;
    private final UserService userService;
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, RefreshTokenRepository refreshTokenRepository, CustomUserDetailsService CustomUserDetailsService, UserService userService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.CustomUserDetailsService = CustomUserDetailsService;
        this.userService = userService;
    }
    @Override
    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
       UserDTO userDTO = userService.createUser(registerRequestDTO);
       System.out.println("User ID = " + userDTO.getId());
       UserDetails userDetails = CustomUserDetailsService.loadUserByUsername(userDTO.getEmail());
       String accessToken = jwtService.generateToken(userDetails);
       RefreshToken refreshToken = createRefreshToken(userDTO.getId());
       AuthResponseDTO authResponseDTO = new AuthResponseDTO(accessToken, refreshToken.getToken());
       return authResponseDTO;
    }
    private RefreshToken createRefreshToken(UUID userId) {

        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserId(userId);

        refreshToken.setToken(
                UUID.randomUUID().toString());

        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(7));

        return refreshTokenRepository.save(refreshToken);
    }
    @Override
    public AuthResponseDTO login(LoginRequestDTO loginRequestDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword()));
        User user=userRepository.findByEmail(loginRequestDTO.getEmail()).orElseThrow(()->new IllegalArgumentException("User not found with email: "+loginRequestDTO.getEmail()));
        UserDetails userDetails=CustomUserDetailsService.loadUserByUsername(loginRequestDTO.getEmail());
        String accessToken=jwtService.generateToken(userDetails);
        RefreshToken refreshToken = createRefreshToken(user.getId());
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(accessToken, refreshToken.getToken());
        return authResponseDTO;

    }
//     private UserDTO mapToUserDTO(User user){
//         if(user==null)return null;
//         UserDTO userDTO=new UserDTO();
//         userDTO.setFullName(user.getFullName());
//         userDTO.setEmail(user.getEmail());
//         userDTO.setPhoneNumber(user.getPhoneNumber());
//         userDTO.setRole(user.getRole());
//         userDTO.setProfilePicture(user.getProfilePicture());
//         userDTO.setIsVerified(user.getIsVerified());
//         userDTO.setRating(user.getRating());
//         userDTO.setCreatedAt(user.getCreatedAt());
//         userDTO.setUpdatedAt(user.getUpdatedAt());
//         return userDTO;
//     }

    @Override
    public AuthResponseDTO refreshToken(String refreshToken) {
       
       RefreshToken token = refreshTokenRepository
            .findByToken(refreshToken)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Refresh token not found"));

    if (token.getExpiryDate().isBefore(LocalDateTime.now())) {

        refreshTokenRepository.delete(token);

        throw new IllegalArgumentException(
                "Refresh token expired");
    }

    User user = userRepository
            .findById(token.getUserId())
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "User not found"));

    UserDetails userDetails =
            CustomUserDetailsService
                    .loadUserByUsername(user.getEmail());

    String accessToken =
            jwtService.generateToken(userDetails);

    AuthResponseDTO response =
            new AuthResponseDTO(accessToken, refreshToken);

    return response;
    }
    @Override
    public void logout(String refreshToken) {
        RefreshToken token = refreshTokenRepository
            .findByToken(refreshToken)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "Refresh token not found"));

    refreshTokenRepository.delete(token);
        
    }
}
