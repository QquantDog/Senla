package com.senla.models.cab;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cab implements Identifiable<Long> {
    private Long cabId;
    private String vin;
    private LocalDate manufactureDate;
    private String colorDescription;
    private String licensePlate;
    private String parkCode;

    private Long cityId;
    private Long vehicleId;

    @Override
    public Long getId() {
        return cabId;
    }

    @Override
    public void setId(Long id) {
        this.cabId = id;
    }
}
