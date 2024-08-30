package com.senla.dto.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerUpdateDto {
    private Long customerId;
    private String currentLat;
    private String currentLong;

    private Long userId;
    private Long addressId;
}
