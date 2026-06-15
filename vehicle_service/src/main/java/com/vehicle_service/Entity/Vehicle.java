package com.vehicle_service.Entity;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name="vehicleEntity")
public class Vehicle {
    


    private UUID id;
    private String ownerName;
    private String email;
    private String phoneNumber;
    private String password;
    private 
}

