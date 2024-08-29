package com.senla.models.driver;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverCreateDto {

    private Boolean isReady;
    private String currentLat;
    private String currentLong;

    //    1->1
    private Long userId;
    private Long cityId;
}
