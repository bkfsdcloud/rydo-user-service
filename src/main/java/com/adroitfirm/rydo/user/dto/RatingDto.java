package com.adroitfirm.rydo.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {
    private Long id;

    @NotNull
    private Long rideId;

    @NotNull
    private Long ratedBy;

    @NotNull
    private Long ratedFor;

    @Min(1)
    @Max(5)
    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;
}