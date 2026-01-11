package com.ridelog.ridelog.vehicleservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleRequest {
    private String name;
    private String type;
    private String model;
    private LocalDate purchaseDate;
    private Long chassisNumber;
    private String registrationNumber;
    private String state;
    private Long odometerReading;
}
