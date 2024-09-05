package com.senla.models.customerrating;

import com.senla.util.repository.Identifiable;
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
public class CustomerRating implements Identifiable<Long> {
    private Long ratingId;
    private BigDecimal rating;
    private LocalDateTime createdAt;
    private String comment;

    private Long rideId;

    @Override
    public Long getId() {
        return ratingId;
    }

    @Override
    public void setId(Long aLong) {
        this.ratingId = aLong;
    }
}
