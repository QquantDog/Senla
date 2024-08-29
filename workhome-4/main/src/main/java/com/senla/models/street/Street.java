package com.senla.models.street;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Street implements Identifiable<Long> {
    private Long streetId;
    private String streetName;

    private Long cityId;

    @Override
    public Long getId() {
        return streetId;
    }

    @Override
    public void setId(Long aLong) {
        this.streetId = aLong;
    }
}
