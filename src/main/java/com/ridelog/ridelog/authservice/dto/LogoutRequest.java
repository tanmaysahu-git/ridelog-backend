package com.ridelog.ridelog.authservice.dto;

public record LogoutRequest(
        String refreshToken
) {
}
