package com.user_service.Controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.user_service.DTO.RegisterRequestDTO;
import com.user_service.DTO.UserDTO;
import com.user_service.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

@PostMapping("/create")
public ResponseEntity<?> createUser(@RequestBody RegisterRequestDTO registerRequestDTO){
return ResponseEntity.ok(userService.createUser(registerRequestDTO));
}
@GetMapping("/email/{email}")
public ResponseEntity<?> getUserByEmail(@PathVariable String email){
    return ResponseEntity.ok(userService.getUserByEmail(email));
}
@GetMapping("/phone")
public ResponseEntity<?> getUserByPhoneNumber(@RequestParam String phoneNumber){
    return ResponseEntity.ok(userService.getUserByPhoneNumber(phoneNumber));
}
@DeleteMapping("/delete/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable UUID id){
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
}
@PutMapping("/update/{id}")
public ResponseEntity<?> updateUser(@PathVariable UUID id, @RequestBody UserDTO userDTO){
    return ResponseEntity.ok(userService.updateUser(id, userDTO));
}
}

