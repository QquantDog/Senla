package com.senla.models.vehiclebrand;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleBrand implements Identifiable<Long> {
    private Long brandId;
    private String brandName;

    @Override
    public Long getId() {
        return brandId;
    }

    @Override
    public void setId(Long id) {
        this.brandId = id;
    }
}
