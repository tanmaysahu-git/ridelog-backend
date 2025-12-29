package com.ridelog.ridelog.model;

import com.ridelog.ridelog.model.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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
