package com.ridelog.ridelog.maintenanceservice.model;

import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "service_records")
public class ServiceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate serviceDate;
    private String description;      // what was serviced
    private Double cost;
    private LocalDate nextServiceDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
}
