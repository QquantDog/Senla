package com.senla.repositories.impl;

import com.senla.models.ride.Ride;
import com.senla.util.repository.LongIdRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class RideRepository extends LongIdRepository<Ride> {
    @PostConstruct
    void init(){
        List<Ride> rides = new ArrayList<>();
        initList(rides);
        super.bulkInit(rides);
    }

    private void initList(List<Ride> rides) {
        Ride ride = new Ride(1L, "DISCOUNT20", BigDecimal.valueOf(0L), BigDecimal.valueOf(1200L), "34.45", "45.35", "35.35", "46.12", LocalDateTime.now(), null, null, null, "active", 1L, 1L, 1L);
        rides.add(ride);
    }

//    private Long rideId;
//    private String promocodeEnterCode;
//    private BigDecimal rideTip;
//    private BigDecimal rideDistanceMeters;
//
//    private String startPointLong;
//    private String startPointLat;
//    private String endPointLong;
//    private String endPointLat;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime rideDriverWaiting;
//    private LocalDateTime rideStartTime;
//    private LocalDateTime rideEndTime;
//    private String status;
//
//    private Long shiftId;
//    private Long customerId;
//    private Long promocodeId;

    @Override
    protected void postSaveProcessEntity(Ride entity) {}
    @Override
    protected void postUpdateProcessEntity(Ride entity) {}


}
