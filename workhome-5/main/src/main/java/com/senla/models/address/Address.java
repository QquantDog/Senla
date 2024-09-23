package com.senla.models.address;

import com.senla.util.repository.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address implements Identifiable<Long> {
    private Long addressId;
    private String name;
    private String longitude;
    private String lat;
    private String description;

    private Long streetId;

    @Override
    public Long getId() {
        return addressId;
    }

    @Override
    public void setId(Long id) {
        this.addressId = id;
    }
}
