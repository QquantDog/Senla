package com.senla.services.impl;

import com.senla.models.ride.Ride;
import com.senla.repositories.impl.RideRepository;
import com.senla.services.RideService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RideServiceImpl extends AbstractLongIdGenericService<Ride> implements RideService {
    @Autowired
    public RideServiceImpl(RideRepository rideRepository) {
        this.abstractRepository = rideRepository;
    }
}
