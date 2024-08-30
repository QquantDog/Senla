package com.senla.services.impl;

import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.repositories.impl.VehicleBrandRepository;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleBrandService extends AbstractLongIdGenericService<VehicleBrand> {
    @Autowired
    public VehicleBrandService(VehicleBrandRepository vehicleBrandRepository) {
        this.abstractRepository = vehicleBrandRepository;
    }
}
