package com.senla.models.shift;

import com.senla.util.repository.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shift implements Identifiable<Long> {
    private Long shiftId;
    private LocalDateTime starttime;
    private LocalDateTime endtime;

    private Long cabId;
    private Long driverId;

    @Override
    public Long getId() {
        return shiftId;
    }

    @Override
    public void setId(Long aLong) {
        this.shiftId = aLong;
    }
}
