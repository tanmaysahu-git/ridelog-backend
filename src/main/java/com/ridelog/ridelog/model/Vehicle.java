package com.ridelog.ridelog.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String type;
    private String model;
    private LocalDate purchaseDate;
    private Long chassisNumber;
    private String registrationNumber;
    private String state;
    private Long odometerReading;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
