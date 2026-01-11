package com.ridelog.ridelog.userservice.model;

import com.ridelog.ridelog.vehicleservice.model.Vehicle;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Check;

import java.util.List;

@Getter
@Setter
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "mobile")
        }
)
@Check(constraints = "email IS NOT NULL OR mobile_number IS NOT NULL")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String name;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @Column(name = "mobile_number", unique = true)
    private String mobileNumber;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    // mappedBy in Vehicle class if using @OneToMany
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

}
