package com.ridelog.ridelog.vehicleservice.service;

import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import com.ridelog.ridelog.vehicleservice.dto.AddVehicleRequest;

import java.util.List;

public interface VehicleService {
    List<Vehicle> getVehiclesByUserId(Long userId);

    void addVehicles(AddVehicleRequest addVehicleRequest);
}
