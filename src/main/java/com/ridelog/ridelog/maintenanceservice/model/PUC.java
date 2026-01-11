package com.ridelog.ridelog.maintenanceservice.model;

import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "puc")
public class PUC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate expiryDate;
    private String status;           // Valid, Expired

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
