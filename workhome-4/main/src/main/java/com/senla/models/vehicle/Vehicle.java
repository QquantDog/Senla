package com.senla.models.vehicle;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle implements Identifiable<Long>{
    private Long vehicleId;
    private String vehicleModel;

    private Long brandId;

    @Override
    public Long getId() {
        return vehicleId;
    }

    @Override
    public void setId(Long id) {
        this.vehicleId = id;
    }
}
