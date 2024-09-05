package com.senla.services.impl;

import com.senla.models.driver.Driver;
import com.senla.repositories.jdbc.DriverJDBCRepo;
import com.senla.services.DriverService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverServiceImpl extends AbstractLongIdGenericService<Driver> implements DriverService {
    @Autowired
    public DriverServiceImpl(DriverJDBCRepo driverRepository) {
        this.abstractRepository = driverRepository;
    }
}
