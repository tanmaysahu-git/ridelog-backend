package com.ridelog.ridelog.maintenanceservice.repository;

import com.ridelog.ridelog.maintenanceservice.model.PUC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PUCRepository extends JpaRepository<PUC, Long> {
    Optional<PUC> findByVehicleId(Long vehicleId);
}
