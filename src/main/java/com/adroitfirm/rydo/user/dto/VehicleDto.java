package com.adroitfirm.rydo.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDto {
    private Long id;

    @NotNull
    private Long driverId;

    @Size(max = 20)
    private String registrationNumber;

    @Size(max = 100)
    private String model;

    @Size(max = 50)
    private String color;

    @Size(max = 30)
    private String vehicleType;

    private Integer capacity;
}