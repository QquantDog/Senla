package com.senla.models.customerrating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRatingCreateDto {
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private String comment;

    private Long rideId;
}
