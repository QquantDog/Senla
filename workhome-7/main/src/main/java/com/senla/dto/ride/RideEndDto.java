package com.senla.dto.ride;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RideEndDto {

    @NotNull
    @JsonProperty("driver_id")
    private Long driverId;

    @NotNull
    @JsonProperty("ride_id")
    private Long rideId;

    @JsonProperty("actual_distance")
    private BigDecimal actualDistance;


}