package com.senla.services.impl;

import com.senla.models.customer.Customer;
import com.senla.models.driver.Driver;
import com.senla.repositories.impl.CustomerRepository;
import com.senla.repositories.impl.DriverRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverService extends AbstractLongIdGenericService<Driver> {
    @Autowired
    public DriverService(DriverRepository driverRepository) {
        this.abstractRepository = driverRepository;
    }
}
