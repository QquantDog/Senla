package com.senla.dto.ride;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RideMatchDto {

    @NotNull
    @JsonProperty("shift_id")
    private Long shiftId;

    @NotNull
    @JsonProperty("ride_id")
    private Long rideId;

}