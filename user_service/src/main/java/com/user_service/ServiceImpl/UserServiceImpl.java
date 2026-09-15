package com.user_service.ServiceImpl;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user_service.DTO.RegisterRequestDTO;
import com.user_service.DTO.UserDTO;
import com.user_service.Entites.User;
import com.user_service.Enums.Role;
import com.user_service.Repository.UserRepository;
import com.user_service.Service.UserService;


@Service
public class UserServiceImpl implements UserService {
    

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
   
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
       
    }
    @Override
    public UserDTO createUser(RegisterRequestDTO registerRequestDTO) {
        Optional<User> existingUserByEmail = userRepository.findByEmail(registerRequestDTO.getEmail());
        if(existingUserByEmail.isPresent()){
            logger.warn("Email already exist {}",registerRequestDTO.getEmail());
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
        user.setRole(
        registerRequestDTO.getRole() != null
        ? registerRequestDTO.getRole()
        : Role.USER
);
        // user.setCreatedAt(registerRequestDTO.getCreatedAt());
        // user.setUpdatedAt(registerRequestDTO.getUpdatedAt());
        User savedUser=userRepository.save(user);
        return mapToUserDTO(savedUser);
    }
    private UserDTO mapToUserDTO(User user){
        if(user==null)return null;

        UserDTO userDTO=new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        return userDTO;
    }
   @Caching(evict = {
    @CacheEvict(value = "usersById", key = "#id"),
    @CacheEvict(value = "usersByEmail", allEntries = true),
    @CacheEvict(value = "usersByPhoneNumber", allEntries = true)
})
    @Override
    public void deleteUser(UUID id) {
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found with id: "+id));
        if(user!=null){
            logger.info("user deleted successfully{}",id);
            userRepository.deleteById(id);
        }
        else{
            throw new IllegalArgumentException("User not found with id: "+id);
        }  
    }
    @Cacheable(value = "usersById", key = "#id")
@Override
public UserDTO getUserById(UUID id) {

    User user = userRepository.findById(id)
            .orElseThrow(() ->
                    new IllegalArgumentException(
                            "User not found with id: " + id));

    return mapToUserDTO(user);
}
    @Cacheable(value="usersByEmail",key="#email")
    @Override
    public Optional<UserDTO> getUserByEmail(String email) {
        User user=userRepository.findByEmail(email).orElseThrow(()->new IllegalArgumentException("User not found with email:"+email));
        if(user!=null){
            logger.info("db hitted by the request");
            return Optional.of(mapToUserDTO(user));
        }
        return Optional.empty();
    }
      @Cacheable(value="usersByPhoneNumber",key="#phoneNumber")
    @Override
    public Optional<UserDTO> getUserByPhoneNumber(String phoneNumber) {
        User user=userRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new IllegalArgumentException("User not found with phone number:"+phoneNumber));
        if(user!=null){
            return Optional.of(mapToUserDTO(user));
        }
        return Optional.empty();
    }
    @Caching(
        evict={
            @CacheEvict(value="usersById",key="#id"),
            @CacheEvict(value="usersByEmail",allEntries = true),
            @CacheEvict(value="usersByPhoneNumber",allEntries=true)
        }
    )
    @Override
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        User user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("User not found with id: "+id));
        if(user!=null){
            user.setFullName(userDTO.getFullName());
             String email=user.getEmail();
             if(user.getEmail()!=null&&email!=userDTO.getEmail()){
            user.setEmail(userDTO.getEmail());
             }
               String phone=user.getPhoneNumber();
             if(user.getPhoneNumber()!=null&&phone!=userDTO.getPhoneNumber()){
            user.setPhoneNumber(userDTO.getPhoneNumber());
             }
            user.setUpdatedAt(userDTO.getUpdatedAt());
            User updatedUser=userRepository.save(user);
            logger.info("updated the user successfully{}",userDTO.getEmail());
            return mapToUserDTO(updatedUser);
        }
        return null;
    }

}
