package com.ridelog.ridelog.vehicleservice.model;

import com.ridelog.ridelog.userservice.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "vehicle")
public class Vehicle {
    @Id
    @GeneratedValue
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
