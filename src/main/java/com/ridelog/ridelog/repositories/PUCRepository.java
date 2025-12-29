package com.ridelog.ridelog.repositories;

import com.ridelog.ridelog.model.PUC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PUCRepository extends JpaRepository<PUC, Long> {
    Optional<PUC> findByVehicleId(Long vehicleId);
}
