package com.user_service.DTO;
import java.util.UUID;
import java.time.LocalDateTime;
import com.user_service.Enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


public class UserDTO {
    private UUID id;

    @NotBlank(message ="Full name is required")
     private String fullName;
     @Email(message="Invalid email Format")
     @NotBlank(message="Email is required")
    private String email;
    @Pattern(regexp="^\\+?[0-9]{10,15}$", message="Invalid phone number format")
    @NotBlank(message="Phone number is required")
    private String phoneNumber;
    private Role role;
    private String profilePicture;
    private Boolean isVerified;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public UserDTO(){

    }
    public UserDTO(UUID id, String fullName, String email, String phoneNumber, Role role, String profilePicture, Boolean isVerified, Double rating, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.profilePicture = profilePicture;
        this.isVerified=isVerified;
        this.rating=rating;
        this.createdAt=createdAt;
        this.updatedAt=updatedAt;

    }
    public UUID getId() {
        return id;
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
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    public Boolean getIsVerified() {
        return isVerified;
    }
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
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
