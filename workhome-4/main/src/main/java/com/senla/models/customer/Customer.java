package com.senla.models.customer;

import com.senla.repositories.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer implements Identifiable<Long> {
    private Long customerId;
    private String currentLat;
    private String currentLong;

    private Long addressId;

    @Override
    public Long getId() {
        return customerId;
    }

    @Override
    public void setId(Long aLong) {
        this.customerId = aLong;
    }
}
