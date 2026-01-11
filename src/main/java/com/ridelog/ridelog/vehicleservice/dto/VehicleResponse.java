package com.ridelog.ridelog.vehicleservice.dto;

import java.time.LocalDate;

public record VehicleResponse(
        Long id,
        String name,
        String type,
        String model,
        LocalDate purchaseDate,
        Long chassisNumber,
        String registrationNumber,
        String state,
        Long odometerReading,
        Long userId
) {
}
