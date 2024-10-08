package com.senla.dto.shift;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftFinishDto {

    @JsonProperty("shift_id")
    private Long shiftId;

    @JsonProperty("driver_id")
    private Long driverId;
}
