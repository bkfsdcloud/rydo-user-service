package com.adroitfirm.rydo.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Owner driver
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(name = "registration_number", length = 20)
    private String registrationNumber;

    @Column(length = 100)
    private String model;

    @Column(length = 50)
    private String color;

    @Column(name = "vehicle_type", length = 30)
    private String vehicleType; // SEDAN, SUV, AUTO, BIKE
    
    @Column(name = "vehicle_category", length = 30)
    private String vehicleCategory; // SMALL, MEDIUM, PREMIUM, LUXURY 

    private Integer capacity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}