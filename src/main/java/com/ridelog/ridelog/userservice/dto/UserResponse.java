package com.ridelog.ridelog.userservice.dto;

import java.util.List;

public record UserResponse(
        Long id,
        String name,
        String email,
        String mobileNumber,
        List<Long> vehicleIds // optional, can exclude for light payloads
) {
}