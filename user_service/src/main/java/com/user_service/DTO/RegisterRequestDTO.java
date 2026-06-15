package com.user_service.DTO;
import com.user_service.Enums.Role;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class RegisterRequestDTO {
    @NotBlank(message="full name is required")
    private String fullName;
    @Email(message="Invalid email format")
    @NotBlank(message="email is required")
    private String email;
    @NotBlank(message="phone number is required")
    @Pattern(
        regexp="^\\+?[0-9]{10,15}$",
        message="Invalid phone number format"
    )
    private String phoneNumber;
    @NotBlank(message="password is required")
    @Size(min=8,message="password must be at least 8 characters")
    private String password;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    public RegisterRequestDTO(){

    }
    public RegisterRequestDTO(String fullName,String email,String phoneNumber,String password,Role role,LocalDateTime createdAt,LocalDateTime updatedAt){
        this.fullName=fullName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.password=password;
        this.role=role;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
}
