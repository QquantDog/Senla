package com.senla.services.impl;

import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.repositories.impl.VehicleBrandRepository;
import com.senla.services.VehicleBrandService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleBrandServiceImpl extends AbstractLongIdGenericService<VehicleBrand> implements VehicleBrandService {
    @Autowired
    public VehicleBrandServiceImpl(VehicleBrandRepository vehicleBrandRepository) {
        this.abstractRepository = vehicleBrandRepository;
    }
}
