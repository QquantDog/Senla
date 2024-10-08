package com.senla.dao.driverregistry;

import com.senla.models.driverregistry.DriverRegistry;
import com.senla.util.dao.GenericDao;

import java.util.List;

public interface IDriverRegistryDao extends GenericDao<DriverRegistry, Long>{
    List<DriverRegistry> getAll();
    List<DriverRegistry> getByDriverId(Long driverId);
    DriverRegistry getEntry(Long driverId, Long companyId);
}
