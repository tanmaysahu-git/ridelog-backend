package com.ridelog.ridelog.authservice.dto;

public record RefreshResponse(
        String accessToken,
        String refreshToken
) {
}
