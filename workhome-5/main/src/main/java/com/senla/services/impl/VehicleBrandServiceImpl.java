package com.senla.services.impl;

import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.repositories.jdbc.VehicleBrandJDBCRepo;
import com.senla.services.VehicleBrandService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleBrandServiceImpl extends AbstractLongIdGenericService<VehicleBrand> implements VehicleBrandService {
    @Autowired
    public VehicleBrandServiceImpl(VehicleBrandJDBCRepo vehicleBrandRepository) {
        this.abstractRepository = vehicleBrandRepository;
    }
}
