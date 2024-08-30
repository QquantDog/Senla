package com.senla.repositories.impl;

import com.senla.models.driver.Driver;
import com.senla.util.repository.LongIdRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DriverRepository extends LongIdRepository<Driver> {
    @PostConstruct
    void init(){
        List<Driver> drivers = new ArrayList<>();
        initList(drivers);
        super.bulkInit(drivers);
    }

    private void initList(List<Driver> drivers) {
        Driver driver1 = new Driver(3L, false, "45.34", "34.65", 1L);
        Driver driver2 = new Driver(4L, true, "48.24", "25.46", 2L);
        drivers.add(driver1);
        drivers.add(driver2);
    }

//    private Long driverId;
//    private Boolean isReady;
//    private String currentLat;
//    private String currentLong;
//

//    private Long cityId;

    @Override
    protected void postSaveProcessEntity(Driver entity) {}
    @Override
    protected void postUpdateProcessEntity(Driver entity) {}

}
