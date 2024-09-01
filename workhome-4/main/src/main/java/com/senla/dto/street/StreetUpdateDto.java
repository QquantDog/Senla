package com.senla.dto.street;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StreetUpdateDto {
    private Long streetId;
    private String streetName;

    private Long cityId;
}
