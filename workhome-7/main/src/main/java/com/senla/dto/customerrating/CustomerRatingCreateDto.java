package com.senla.dto.customerrating;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
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
public class CustomerRatingCreateDto {

    @NotNull
    private BigDecimal rating;


    private String comment;

//    НЕОБХОДИМО брать со спринг секьюрити
    @NotNull
    @JsonProperty("customer_id")
    private Long customerId;

    @NotNull
    @JsonProperty("ride_id")
    private Long rideId;
}