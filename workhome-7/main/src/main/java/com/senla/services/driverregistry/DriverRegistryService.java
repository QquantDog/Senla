package com.senla.services.driverregistry;

import com.senla.dto.driverregistry.DriverRegistryCreateDto;
import com.senla.dto.driverregistry.DriverRegistryUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.models.driverregistry.DriverRegistry;
import com.senla.util.service.GenericService;

import java.util.List;

public interface DriverRegistryService extends GenericService<DriverRegistry, Long> {
    DriverRegistry registerDriverId(DriverRegistryCreateDto driverRegistryCreateDto) throws DaoCheckedException;
    List<DriverRegistry> findAllEntries();
    List<DriverRegistry> findDriverCompanies(Long companyId);
    DriverRegistry updateRegistration(DriverRegistryUpdateDto dto);
}
