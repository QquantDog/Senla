package com.senla.models.promocode;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Promocode implements Identifiable<Long> {
    private Long promocodeId;
    private String promocodeCode;
    private BigDecimal discountValue;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;

    @Override
    public Long getId() {
        return promocodeId;
    }

    @Override
    public void setId(Long aLong) {
        this.promocodeId = aLong;
    }
}
