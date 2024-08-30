package com.senla.dto.customerrating;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRatingUpdateDto {
    private Long ratingId;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private String comment;

    private Long rideId;
}
