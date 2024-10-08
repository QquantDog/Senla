package com.senla.dto.driver;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverUpdateDto {

    @JsonProperty(value = "is_on_shift")
    private Boolean isOnShift;

    @JsonProperty(value = "is_on_ride")
    private Boolean isOnRide;

    @JsonProperty(value = "current_lat")
    private BigDecimal currentLat;

    @JsonProperty(value = "current_long")
    private BigDecimal currentLong;

    @JsonProperty(value = "current_point")
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    private Point currentPoint;
}
