package com.ridelog.ridelog.vehicleservice.controller;

import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import com.ridelog.ridelog.vehicleservice.dto.AddVehicleRequest;
import com.ridelog.ridelog.vehicleservice.dto.VehicleResponse;
import com.ridelog.ridelog.common.response.ActionResponse;
import com.ridelog.ridelog.common.response.ListResponse;
import com.ridelog.ridelog.vehicleservice.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@Slf4j
public class VehicleController {
    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/addVehicles")
    public ResponseEntity<ActionResponse> addVehicles(@RequestBody AddVehicleRequest addVehicleRequest) {
        log.info("Received request to add {} vehicles to userId {}", addVehicleRequest.getVehicles().size(), addVehicleRequest.getUserId());
        vehicleService.addVehicles(addVehicleRequest);
        return ResponseEntity.ok(new ActionResponse(true, "Successfully added " + addVehicleRequest.getVehicles().size() + " vehicles to userId : " + addVehicleRequest.getUserId()));
    }

    @GetMapping("/getVehiclesByUserId/{userId}")
    public ResponseEntity<ListResponse<VehicleResponse>> getVehiclesByUserId(
            @PathVariable("userId") Long userId) {

        log.info("Received request to fetch vehicles for userId: {}", userId);

        List<Vehicle> vehicles = vehicleService.getVehiclesByUserId(userId);
        List<VehicleResponse> vehicleResponses = vehicles.stream()
                .map(v -> new VehicleResponse(
                        v.getId(),
                        v.getName(),
                        v.getType(),
                        v.getModel(),
                        v.getPurchaseDate(),
                        v.getChassisNumber(),
                        v.getRegistrationNumber(),
                        v.getState(),
                        v.getOdometerReading(),
                        v.getUser().getId() // omit user to prevent recursion
                ))
                .toList();
        ListResponse<VehicleResponse> response = new ListResponse<>(vehicleResponses,
                true,
                "successfully received all vehicles");
        return ResponseEntity.ok(response);
    }
}
