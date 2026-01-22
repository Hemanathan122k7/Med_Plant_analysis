package com.medicinal.plant.service;

import com.medicinal.plant.model.dto.request.AuthRequest;
import com.medicinal.plant.model.dto.request.UserRegistrationRequest;
import com.medicinal.plant.model.dto.response.AuthResponse;
import com.medicinal.plant.model.dto.response.UserResponse;

/**
 * User Service Interface
 */
public interface UserService {

    UserResponse registerUser(UserRegistrationRequest request);

    AuthResponse login(AuthRequest request);

    UserResponse getUserById(Long id);
}
