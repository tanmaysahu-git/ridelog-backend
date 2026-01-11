package com.ridelog.ridelog.maintenanceservice.repository;

import com.ridelog.ridelog.maintenanceservice.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
    Optional<Insurance> findByVehicleId(Long vehicleId);
}
