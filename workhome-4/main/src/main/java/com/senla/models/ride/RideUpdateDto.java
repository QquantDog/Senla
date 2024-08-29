package com.senla.models.ride;

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
public class RideUpdateDto {
    private Long rideId;
    private String promocodeEnterCode;
    private BigDecimal rideTip;
    private BigDecimal rideDistanceMeters;

    private String startPointLong;
    private String startPointLat;
    private String endPointLong;
    private String endPointLat;

    private LocalDateTime createdAt;
    private LocalDateTime rideDriverWaiting;
    private LocalDateTime rideStartTime;
    private LocalDateTime rideEndTime;
    private String status;

    private Long shiftId;
    private Long customerId;
    private Long promocodeId;
}