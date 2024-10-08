package com.senla.services.impl;

import com.senla.dao.vehiclebrand.VehicleBrandDao;
import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.services.VehicleBrandService;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleBrandServiceImpl extends AbstractLongIdGenericService<VehicleBrand> implements VehicleBrandService {
    @Autowired
    private VehicleBrandDao vehicleBrandDao;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = vehicleBrandDao;
    }
}

