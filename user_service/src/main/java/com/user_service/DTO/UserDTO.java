package com.user_service.DTO;

import java.time.LocalDateTime;
import com.user_service.Enums.Role;


public class UserDTO {
     private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String profilePicture;
    private Boolean isVerified;
    private Double rating;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public UserDTO(){

    }
    public UserDTO(String fullName,String email, String phoneNumber,Role role,String profilePicture,Boolean isVerified,Double rating,LocalDateTime createdAt,LocalDateTime updatedAt){
        this.fullName=fullName;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.role=role;
        this.profilePicture=profilePicture;
        this.isVerified=isVerified;
        this.rating=rating;
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
