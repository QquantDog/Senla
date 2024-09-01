package com.senla.repositories.impl;


import com.senla.models.vehicle.Vehicle;
import com.senla.util.repository.LongIdRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleRepository extends LongIdRepository<Vehicle> {
    @PostConstruct
    void init(){
        List<Vehicle> vehicles = new ArrayList<>();
        initList(vehicles);
        super.bulkInit(vehicles);
    }

    private void initList(List<Vehicle> vehicles) {
        Vehicle v1 = new Vehicle(1L, "TT", 1L);
        Vehicle v2 = new Vehicle(2L, "Polo", 2L);
        Vehicle v3 = new Vehicle(3L, "Rapid", 3L);
        Vehicle v4 = new Vehicle(4L, "Corolla", 4L);
        Vehicle v5 = new Vehicle(5L, "Camry", 4L);
        vehicles.add(v1);
        vehicles.add(v2);
        vehicles.add(v3);
        vehicles.add(v4);
        vehicles.add(v5);
    }

    @Override
    protected void postSaveProcessEntity(Vehicle entity) {}
    @Override
    protected void postUpdateProcessEntity(Vehicle entity) {}

}
