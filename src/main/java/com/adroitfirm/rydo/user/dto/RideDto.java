package com.adroitfirm.rydo.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideDto {
    private Long id;

    @NotNull
    private Long customerId;

    private Long driverId;

    @Size(max = 255)
    private String pickupLocation;

    @Size(max = 255)
    private String dropLocation;

    private Double pickupLat;
    private Double pickupLng;
    private Double dropLat;
    private Double dropLng;

    private BigDecimal fareEstimated;
    private BigDecimal fareFinal;
    private BigDecimal distanceKm;
    private Integer durationMinutes;

    @Size(max = 20)
    private String status;

    private LocalDateTime requestedAt;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;
}