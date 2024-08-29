package com.senla.models.city;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class City implements Identifiable<Long> {
    private Long cityId;
    private String cityName;

    @Override
    public Long getId() {
        return cityId;
    }

    @Override
    public void setId(Long aLong) {
        this.cityId = aLong;
    }
}
