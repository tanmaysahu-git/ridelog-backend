package com.ridelog.ridelog.authservice.dto;


public record LoginResponse(
        String userName,
        String accessToken,
        String refreshToken
) {
}