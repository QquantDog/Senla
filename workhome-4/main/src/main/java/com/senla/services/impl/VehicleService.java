package com.senla.services.impl;

import com.senla.models.vehicle.Vehicle;
import com.senla.repositories.impl.VehicleBrandRepository;
import com.senla.repositories.impl.VehicleRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleService extends AbstractLongIdGenericService<Vehicle> {
    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.abstractRepository = vehicleRepository;
    }
}
