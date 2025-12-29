package com.ridelog.ridelog.model;

import com.ridelog.ridelog.model.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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
