package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.config.SpringConfig;
import com.senla.controllers.RideController;
import com.senla.dto.ride.RideCreateDto;
import com.senla.dto.ride.RideUpdateDto;
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
        RideCreateDto rideCreateDto = RideCreateDto.builder()
                .shiftId(1L)
                .customerId(1L)
                .promocodeId(null)
                .promocodeEnterCode(null)
                .rideTip(BigDecimal.valueOf(2.0))
                .rideDistanceMeters(BigDecimal.valueOf(5000.0))
                .startPointLat(BigDecimal.valueOf(44.44))
                .startPointLong(BigDecimal.valueOf(44.44))
                .endPointLat(BigDecimal.valueOf(44.44))
                .endPointLong(BigDecimal.valueOf(44.44))
                .createdAt(LocalDateTime.now())
                .rideDriverWaiting(null)
                .rideStartTime(null)
                .rideEndTime(null)
                .status("accepted")
                    .build();
        System.out.println("TO INSERT: " + c.createRide(rideCreateDto));
        System.out.println("ALL after insertion: " + c.getAllRides());

        RideUpdateDto rideUpdateDto = RideUpdateDto.builder()
                .rideId(3L)
                .shiftId(1L)
                .customerId(1L)
                .promocodeId(null)
                .promocodeEnterCode(null)
                .rideTip(BigDecimal.valueOf(2.0))
                .rideDistanceMeters(BigDecimal.valueOf(5000.0))
                .startPointLat(BigDecimal.valueOf(55.44))
                .startPointLong(BigDecimal.valueOf(55.44))
                .endPointLat(BigDecimal.valueOf(55.44))
                .endPointLong(BigDecimal.valueOf(55.44))
                .createdAt(LocalDateTime.now())
                .rideDriverWaiting(null)
                .rideStartTime(null)
                .rideEndTime(null)
                .status("accepted")
                    .build();
        System.out.println("TO UPDATE: " + c.updateRide(rideUpdateDto));
        System.out.println("ALL after updation: " + c.getAllRides());

        c.existsRide(3L);
        c.deleteRide(3L);
        System.out.println("ALL after deletion: " + c.getAllRides());
    }
}
