package com.senla.dto.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverResponseDto {

    private Boolean isReady;
    private String currentLat;
    private String currentLong;

    private Long cityId;
}
