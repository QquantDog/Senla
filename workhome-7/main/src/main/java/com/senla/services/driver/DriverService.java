package com.senla.services.driver;

import com.senla.dto.driver.DriverUpdateDto;
import com.senla.dto.promocode.PromocodeUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.models.driver.Driver;
import com.senla.models.driverregistry.DriverRegistry;
import com.senla.models.promocode.Promocode;
import com.senla.util.service.GenericService;

import java.time.LocalDateTime;

public interface DriverService extends GenericService<Driver, Long> {
    Driver updateDriver(Long id, Driver driver) throws DaoCheckedException;
}
