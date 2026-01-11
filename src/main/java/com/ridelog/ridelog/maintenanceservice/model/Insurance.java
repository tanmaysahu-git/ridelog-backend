package com.ridelog.ridelog.maintenanceservice.model;

import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provider;
    private LocalDate expiryDate;
    private Double premium;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
