package com.senla.models.ride;

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
public class Ride implements Identifiable<Long> {
    private Long rideId;
    private String promocodeEnterCode;
    private BigDecimal rideTip;
    private BigDecimal rideDistanceMeters;

    private BigDecimal startPointLong;
    private BigDecimal startPointLat;
    private BigDecimal endPointLong;
    private BigDecimal endPointLat;

    private LocalDateTime createdAt;
    private LocalDateTime rideDriverWaiting;
    private LocalDateTime rideStartTime;
    private LocalDateTime rideEndTime;
    private String status;

    private Long shiftId;
    private Long customerId;
    private Long promocodeId;

    @Override
    public Long getId() {
        return rideId;
    }

    @Override
    public void setId(Long aLong) {
        this.rideId = aLong;
    }
}
