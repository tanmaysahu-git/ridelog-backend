package com.ridelog.ridelog.authservice.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        String userName,
        @NotBlank(message = "Password is mandatory")
        String password
) {
}
