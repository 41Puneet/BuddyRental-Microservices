package com.vehicle_service.DTO;

import java.util.UUID;

/**
 * Minimal projection of user_service's UserDTO — only fields needed by vehicle_service.
 */
public class UserDTO {

    private UUID id;
    private String fullName;
    private String email;
    private String role;
    private Boolean isVerified;

    public UserDTO() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Boolean getIsVerified() { return isVerified; }
    public void setIsVerified(Boolean isVerified) { this.isVerified = isVerified; }
}
