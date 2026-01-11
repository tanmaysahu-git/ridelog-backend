package com.ridelog.ridelog.vehicleservice.impl;

import com.ridelog.ridelog.userservice.model.User;
import com.ridelog.ridelog.common.exception.ServiceException;
import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import com.ridelog.ridelog.vehicleservice.dto.AddVehicleRequest;
import com.ridelog.ridelog.userservice.repository.UserRepository;
import com.ridelog.ridelog.vehicleservice.repository.VehicleRepository;
import com.ridelog.ridelog.vehicleservice.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VehicleServiceImpl implements VehicleService {
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Vehicle> getVehiclesByUserId(Long userId) {
        try {
            log.info("Entering getVehiclesByUserId : UserId : {}", userId);
            return vehicleRepository.findByUserId(userId);
        } catch (ServiceException e) {
            log.error("Error while fetching all Vehicle", e);
            throw new ServiceException("Failed to fetch Users");
        }

    }

    @Override
    public void addVehicles(AddVehicleRequest addVehicleRequest) {
        try {
            log.info("Entering addVehicles for userId : {}", addVehicleRequest.getUserId());
            User user = userRepository.findById(addVehicleRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            List<Vehicle> vehicleEntities = addVehicleRequest.getVehicles().stream()
                    .map(v -> {
                        Vehicle vehicle = new Vehicle();
                        vehicle.setRegistrationNumber(v.getRegistrationNumber());
                        vehicle.setType(v.getType());
                        vehicle.setUser(user);
                        vehicle.setName(v.getName());
                        vehicle.setModel(v.getModel());
                        vehicle.setState(v.getState());
                        vehicle.setChassisNumber(v.getChassisNumber());
                        vehicle.setOdometerReading(v.getOdometerReading());
                        vehicle.setPurchaseDate(v.getPurchaseDate());
                        return vehicle;
                    })
                    .toList();

            vehicleRepository.saveAll(vehicleEntities);
        } catch (ServiceException exception) {
            log.error("Exception occurred while saving vehicles ", exception);
            throw new ServiceException("Exception occurred while saving vehicles");
        }
    }
}
