package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.config.SpringConfig;
import com.senla.controllers.RideController;
import com.senla.dto.ride.RideCreateDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RideRunner {
    public static void main(String[] args) throws JsonProcessingException {
        var ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        RideController c = ctx.getBean(RideController.class);
        testRead(c);
        testBulk(c);
    }

    private static void testRead(RideController c) throws JsonProcessingException {
        System.out.println("ALL: " + c.getAllRides());
        System.out.println("By ID: " + c.getRideById(1L));
    }

    private static void testBulk(RideController c) throws JsonProcessingException {
        RideCreateDto ride = RideCreateDto.builder().rideTip(BigDecimal.valueOf(3L))
                .rideDistanceMeters(BigDecimal.valueOf(5600L))
                .rideStartTime(LocalDateTime.of(2024,4,5,6,7))
                .createdAt(LocalDateTime.now())
                .shiftId(1L)
                .customerId(1L)
                .promocodeEnterCode("weqwe")
                .promocodeId(null)
                .startPointLat("34.23")
                .startPointLong("23.43")
                .endPointLat("34")
                .endPointLong("35.23")
                .status("weqrqwgweqg")
                    .build();
        System.out.println("TO INSERT: " + c.createRide(ride));

        System.out.println("ALL: " + c.getAllRides());

        c.existsRide(1L);
        c.deleteRide(1L);
        c.existsRide(1L);
        System.out.println("ALL: " + c.getAllRides());

    }
}
