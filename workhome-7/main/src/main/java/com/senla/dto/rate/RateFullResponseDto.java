package com.senla.dto.rate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.city.CityResponseDto;
import com.senla.dto.ratetier.RateTierResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RateFullResponseDto {

    @NotNull
    @JsonProperty("init_price")
    private BigDecimal initPrice;

    @NotNull
    @JsonProperty("rate_per_km")
    private BigDecimal ratePerKm;

    @NotNull
    @JsonProperty("paid_waiting_per_minute")
    private BigDecimal paidWaitingPerMinute;

    @NotNull
    @JsonProperty("secccs")
    private Integer freeTimeInSeconds;

    @JsonProperty("city_info")
    private CityResponseDto city;

    @JsonProperty("rate_tier_info")
    private RateTierResponseDto rateTier;
}