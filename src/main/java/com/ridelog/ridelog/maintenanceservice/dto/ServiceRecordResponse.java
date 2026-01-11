package com.ridelog.ridelog.maintenanceservice.dto;

import com.ridelog.ridelog.vehicleservice.dto.VehicleResponse;

import java.time.LocalDate;

public record ServiceRecordResponse(
        Long id,
        LocalDate serviceDate,
        String description,
        Double cost,
        LocalDate nextServiceDate,
        VehicleResponse vehicle
) {
}
