package com.senla.dto.vehicle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleUpdateDto {
    private Long vehicleId;
    private String vehicleModel;

    private Long brandId;
}
