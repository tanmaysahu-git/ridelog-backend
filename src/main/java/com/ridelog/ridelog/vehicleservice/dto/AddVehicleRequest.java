package com.ridelog.ridelog.vehicleservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddVehicleRequest {
    private Long userId;
    private List<VehicleRequest> vehicles;
}