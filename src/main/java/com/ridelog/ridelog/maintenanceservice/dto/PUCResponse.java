package com.ridelog.ridelog.maintenanceservice.dto;

import com.ridelog.ridelog.vehicleservice.dto.VehicleResponse;

import java.time.LocalDate;

public record PUCResponse(
        Long id,
        LocalDate expiryDate,
        String status,
        VehicleResponse vehicle
) {
}
