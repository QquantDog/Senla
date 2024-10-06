package com.senla.dao.vehicle;

import com.senla.models.vehicle.Vehicle;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class VehicleDao extends AbstractLongDao<Vehicle> implements IVehicleDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Vehicle.class;
    }
}
