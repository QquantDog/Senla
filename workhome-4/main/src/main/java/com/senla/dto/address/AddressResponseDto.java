package com.senla.dto.address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponseDto {
    private String name;
    private String longitude;
    private String lat;
    private String description;

    private Long streetId;
}