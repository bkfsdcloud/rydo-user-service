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
public class PaymentDto {
    private Long id;

    @NotNull
    private Long rideId;

    private BigDecimal amount;

    @Size(max = 20)
    private String paymentMethod;

    @Size(max = 100)
    private String transactionId;

    @Size(max = 20)
    private String status;

    private LocalDateTime createdAt;
}