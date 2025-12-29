package com.ridelog.ridelog.model;

import com.ridelog.ridelog.model.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
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
