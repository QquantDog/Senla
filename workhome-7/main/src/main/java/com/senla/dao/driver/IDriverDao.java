package com.senla.dao.driver;

import com.senla.models.driver.Driver;
import com.senla.util.dao.GenericDao;
import jakarta.persistence.Tuple;

import java.util.List;

public interface IDriverDao extends GenericDao<Driver, Long> {
    Driver bindToUser(Long userId);
    Driver getDriverWithCompanies(Long driverId);

    Tuple findDriverCabWithinCompanies(Long driverId, Long cabId);
}
