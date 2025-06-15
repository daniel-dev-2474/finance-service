package com.gymapp.financeservice.client;

import com.gymapp.financeservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Declarative HTTP client for communicating with the user-service.
 * Used to validate user existence before processing purchases.
 */
@FeignClient(name = "user-service", path = "/api/users")
public interface UserClient {

  /**
   * Retrieves a user by ID to ensure it exists.
   *
   * @param userId the unique identifier of the user.
   * @return a {@link UserResponse} object representing the user profile.
   */
  @GetMapping("/{id}")
  UserResponse getUserById(@PathVariable("id") String userId);
}