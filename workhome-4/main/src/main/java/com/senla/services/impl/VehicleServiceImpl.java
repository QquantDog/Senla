package com.senla.services.impl;

import com.senla.models.vehicle.Vehicle;
import com.senla.repositories.impl.VehicleRepository;
import com.senla.services.VehicleService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleServiceImpl extends AbstractLongIdGenericService<Vehicle> implements VehicleService {
    @Autowired
    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.abstractRepository = vehicleRepository;
    }
}
