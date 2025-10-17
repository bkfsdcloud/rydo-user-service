package com.adroitfirm.rydo.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDto {
    private Long id;

    @NotNull
    private Long userId;

    private Long vehicleId;

    private BigDecimal ratingAvg;

    @Size(max = 20)
    private String status;

    private Boolean verified;
}