package com.senla.services.impl;

import com.senla.models.ride.Ride;
import com.senla.repositories.impl.RideRepository;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RideService extends AbstractLongIdGenericService<Ride> {
    @Autowired
    public RideService(RideRepository rideRepository) {
        this.abstractRepository = rideRepository;
    }
}
