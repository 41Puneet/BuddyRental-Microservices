package com.vehicle_service.FeignClient;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vehicle_service.DTO.UserDTO;

/**
 * Feign client to call user_service for owner verification before listing a vehicle.
 */
@FeignClient(name = "user-service")
public interface UserFeignClient {

    @GetMapping("/api/users/{id}")
    UserDTO getUserById(@PathVariable("id") UUID id);
}
