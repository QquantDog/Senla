package com.senla.models.cityrate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRateCreateDto {
    private BigDecimal initPrice;
    private BigDecimal ratePerKm;
    private BigDecimal paidWaitingPerMinute;
    private Integer freeTimeInSeconds;

    private Long cityId;
}