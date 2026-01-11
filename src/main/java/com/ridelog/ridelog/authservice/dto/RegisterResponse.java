package com.ridelog.ridelog.authservice.dto;

public record RegisterResponse(
        Long id,
        String name,
        String email,
        String message
) {
}