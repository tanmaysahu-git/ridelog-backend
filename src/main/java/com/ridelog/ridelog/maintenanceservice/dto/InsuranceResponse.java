package com.ridelog.ridelog.maintenanceservice.dto;

import com.ridelog.ridelog.vehicleservice.dto.VehicleResponse;

import java.time.LocalDate;

public record InsuranceResponse(
        Long id,
        String provider,
        LocalDate expiryDate,
        Double premium,
        VehicleResponse vehicle
) {
}