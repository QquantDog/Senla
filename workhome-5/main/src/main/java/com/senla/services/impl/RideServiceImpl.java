package com.senla.services.impl;

import com.senla.annotation.MyTransaction;
import com.senla.dto.ride.RideCreateDto;
import com.senla.models.ride.Ride;
import com.senla.services.RideService;
import com.senla.repositories.jdbc.RideJDBCRepo;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class RideServiceImpl extends AbstractLongIdGenericService<Ride> implements RideService {
    @Autowired
    public RideServiceImpl(RideJDBCRepo rideRepository) {
        this.abstractRepository = rideRepository;
    }

    @Override
    @MyTransaction
    public void transactionSuccessTest() {
        abstractRepository.save(getSampleRide());
        abstractRepository.save(getSample2Ride());
    }

    @Override
    @MyTransaction
    public void transactionFailureTest() {
        abstractRepository.save(getSampleRide());
        abstractRepository.save(getSample2Ride());
        throw new RuntimeException("wee");
    }


    @Override
    @MyTransaction
    public void multipleInsertTransactionTest(){
        for(int i = 0; i < 100; i++){
            abstractRepository.save(getSampleRide());
        }
    }

    @Override
    public void NONTransactionTest(){
        for(int i = 0; i < 100; i++){
            abstractRepository.save(getSampleRide());
        }
    }

    @Override
    public void partialNonTransactionInsertTest(){
        for(int i = 0; i < 20; i++){
            try{
                if(i > 10) throw new RuntimeException();
                abstractRepository.save(getSampleRide());
            } catch (RuntimeException ignored){ }
        }
    }

    private Ride getSampleRide(){
        return Ride.builder()
                .shiftId(4L)
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
                .status("completed")
                    .build();
    }
    private Ride getSample2Ride(){
        return Ride.builder()
                .shiftId(3L)
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
    }
}
