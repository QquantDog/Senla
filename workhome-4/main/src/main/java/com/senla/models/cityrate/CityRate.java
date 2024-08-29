package com.senla.models.cityrate;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CityRate implements Identifiable<Long> {
    private Long rateId;
    private BigDecimal initPrice;
    private BigDecimal ratePerKm;
    private BigDecimal paidWaitingPerMinute;
    private Integer freeTimeInSeconds;

    private Long cityId;

    @Override
    public Long getId() {
        return rateId;
    }

    @Override
    public void setId(Long aLong) {
        this.rateId = aLong;
    }
}
