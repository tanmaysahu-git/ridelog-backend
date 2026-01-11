package com.ridelog.ridelog.authservice.controller;

import com.ridelog.ridelog.common.constants.ErrorCodes;
import com.ridelog.ridelog.authservice.dto.*;
import com.ridelog.ridelog.authservice.security.JwtService;
import com.ridelog.ridelog.authservice.security.UserDetailsServiceImpl;
import com.ridelog.ridelog.common.exception.ServiceException;
import com.ridelog.ridelog.common.response.ActionResponse;
import com.ridelog.ridelog.userservice.model.User;
import com.ridelog.ridelog.userservice.dto.UserResponse;
import com.ridelog.ridelog.common.response.ApiResponse;
import com.ridelog.ridelog.authservice.service.AuthService;
import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        log.info("Received request to create user: {}", registerRequest.name());

        User registerUser = authService.registerUser(registerRequest);

        log.info("User created successfully with id: {}", registerUser.getId());
        UserResponse userResponse = new UserResponse(registerUser.getId(), registerRequest.name(), registerUser.getEmail(), registerUser.getMobileNumber(),
                Optional.ofNullable(registerUser.getVehicles())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(Vehicle::getId)
                        .toList());
        ApiResponse<UserResponse> response = new ApiResponse<>(
                userResponse,
                true,
                "User created successfully"
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                authService.login(request)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshResponse> refresh(
            @RequestBody RefreshRequest request) {

        return ResponseEntity.ok(
                authService.refresh(request)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<ActionResponse> logout(
            @RequestBody LogoutRequest request,
            Authentication authentication) {

        authService.logout(
                authentication.getName(),
                request.refreshToken()
        );

        return ResponseEntity.ok(
                new ActionResponse(true, "Logged out successfully")
        );
    }

}
