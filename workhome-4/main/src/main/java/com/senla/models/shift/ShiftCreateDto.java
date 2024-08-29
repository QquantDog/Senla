package com.senla.models.shift;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShiftCreateDto {
    private Timestamp starttime;
    private Timestamp endtime;

    private Long cabId;
    private Long driverId;
}
