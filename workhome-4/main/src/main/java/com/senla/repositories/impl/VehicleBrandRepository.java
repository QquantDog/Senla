package com.senla.repositories.impl;

import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.util.repository.LongIdRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VehicleBrandRepository extends LongIdRepository<VehicleBrand> {
    @PostConstruct
    void init(){
        List<VehicleBrand> brands = new ArrayList<>();
        initList(brands);
        super.bulkInit(brands);
    }

    private void initList(List<VehicleBrand> brands) {
        VehicleBrand vb1 = new VehicleBrand(1L, "Audi");
        VehicleBrand vb2 = new VehicleBrand(2L, "Volkswagen");
        VehicleBrand vb3 = new VehicleBrand(3L, "Skoda");
        VehicleBrand vb4 = new VehicleBrand(4L, "Toyota");
        brands.add(vb1);
        brands.add(vb2);
        brands.add(vb3);
        brands.add(vb4);
    }

    @Override
    protected void postSaveProcessEntity(VehicleBrand entity) {}
    @Override
    protected void postUpdateProcessEntity(VehicleBrand entity) {}

}