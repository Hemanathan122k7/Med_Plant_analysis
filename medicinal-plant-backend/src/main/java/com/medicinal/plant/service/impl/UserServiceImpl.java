package com.medicinal.plant.service.impl;

import com.medicinal.plant.exception.UserNotFoundException;
import com.medicinal.plant.model.dto.request.AuthRequest;
import com.medicinal.plant.model.dto.request.UserRegistrationRequest;
import com.medicinal.plant.model.dto.response.AuthResponse;
import com.medicinal.plant.model.dto.response.UserResponse;
import com.medicinal.plant.model.entity.User;
import com.medicinal.plant.model.enums.UserRole;
import com.medicinal.plant.repository.UserRepository;
import com.medicinal.plant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User Service Implementation
 */
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(UserRole.USER)
                .isActive(true)
                .build();
        
        user = userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        // Simplified authentication (JWT generation would go here)
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        UserResponse userResponse = modelMapper.map(user, UserResponse.class);
        
        return AuthResponse.builder()
                .token("dummy-jwt-token-" + user.getId())
                .user(userResponse)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
        return modelMapper.map(user, UserResponse.class);
    }
}
