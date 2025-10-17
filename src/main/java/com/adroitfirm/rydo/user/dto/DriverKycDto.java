package com.adroitfirm.rydo.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverKycDto {
    private Long id;

    @NotNull
    private Long driverId;

    @Size(max = 20)
    private String kycStatus;

    private Long verifiedBy;

    private LocalDateTime verifiedAt;

    private String remarks;
}