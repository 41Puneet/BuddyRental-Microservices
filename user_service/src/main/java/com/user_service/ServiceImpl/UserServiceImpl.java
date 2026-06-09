package com.user_service.ServiceImpl;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user_service.DTO.RegisterRequestDTO;
import com.user_service.DTO.UserDTO;
import com.user_service.Entites.User;
import com.user_service.Repository.UserRepository;
import com.user_service.Security.JwtService;
import com.user_service.Service.UserService;

@Service
public class UserServiceImpl implements UserService {
    



    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }
    @Override
    public UserDTO createUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUserByEmail = userRepository.findByEmail(registerRequestDTO.getEmail());
        if(existingUserByEmail.isPresent()){
            throw new IllegalArgumentException("Email already exists");
        }
        Optional<User> existingUserByPhoneNumber = userRepository.findByPhoneNumber(registerRequestDTO.getPhoneNumber());
        if (existingUserByPhoneNumber.isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }
        User user=new User();
        user.setFullName(registerRequestDTO.getFullName());
        user.setEmail(registerRequestDTO.getEmail());
        user.setPhoneNumber(registerRequestDTO.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        user.setRole(registerRequestDTO.getRole());
        user.setCreatedAt(registerRequestDTO.getCreatedAt());
        user.setUpdatedAt(registerRequestDTO.getUpdatedAt());
        User savedUser=userRepository.save(user);
        return mapToUserDTO(savedUser);
    }
    private UserDTO mapToUserDTO(User user){
        if(user==null)return null;
        UserDTO userDTO=new UserDTO();
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
    @Override
    public void deleteUserByEmail(String email) {
        // TODO Auto-generated method stub
        
    }
    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        // TODO Auto-generated method stub
        return null;
    }

}
