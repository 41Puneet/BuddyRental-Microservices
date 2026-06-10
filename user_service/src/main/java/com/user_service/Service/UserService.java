package com.user_service.Service;
import com.user_service.DTO.UserDTO;
import com.user_service.DTO.RegisterRequestDTO;
import java.util.Optional;
import java.util.UUID;


public interface UserService {
    UserDTO createUser(RegisterRequestDTO registerRequestDTO);
    Optional <UserDTO> getUserByEmail(String email);
    Optional <UserDTO> getUserByPhoneNumber(String phoneNumber);
    void deleteUser(UUID id);
    UserDTO updateUser(UUID id,UserDTO userDTO);
}
