package com.senla.models.vehiclebrand;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBrandUpdateDto {
    private Long brandId;
    private String brandName;
}
