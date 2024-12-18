package com.senla.dto.cab;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabCreateDto {
    private String vin;
    private LocalDate manufactureDate;
    private String colorDescription;
    private String licensePlate;
    private String parkCode;

    private Long cityId;
    private Long vehicleId;
}