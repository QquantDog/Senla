package com.senla.models.driver;

import com.senla.util.repository.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver implements Identifiable<Long> {

    private Long driverId;
    private Boolean isReady;
    private String currentLat;
    private String currentLong;

    private Long cityId;

    @Override
    public Long getId() {
        return driverId;
    }

    @Override
    public void setId(Long aLong) {
        this.driverId = aLong;
    }
}
