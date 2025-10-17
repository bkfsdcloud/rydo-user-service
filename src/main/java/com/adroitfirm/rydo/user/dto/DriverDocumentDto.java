package com.adroitfirm.rydo.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverDocumentDto {
    private Long id;

    @NotNull
    private Long driverId;

    @Size(max = 50)
    private String documentType;

    @Size(max = 50)
    private String documentNumber;

    @Size(max = 255)
    private String documentUrl;

    private LocalDate expiryDate;

    private Boolean verified;
}