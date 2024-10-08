package com.senla.services.driverregistry;

import com.senla.dao.driver.DriverDao;
import com.senla.dao.driverregistry.DriverRegistryDao;
import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.dto.driverregistry.DriverRegistryCreateDto;
import com.senla.dto.driverregistry.DriverRegistryUpdateDto;
import com.senla.exceptions.DaoCheckedException;
import com.senla.exceptions.DaoException;
import com.senla.models.driver.Driver;
import com.senla.models.driverregistry.DriverRegistry;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DriverRegistryServiceImpl extends AbstractLongIdGenericService<DriverRegistry> implements DriverRegistryService {

    @Autowired
    private DriverRegistryDao driverRegistryDao;

    @Autowired
    private TaxiCompanyDao taxiCompanyDao;

    @Autowired
    private DriverDao driverDao;
    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = driverRegistryDao;
    }

    @Override
    @Transactional
    public DriverRegistry registerDriverId(DriverRegistryCreateDto driverRegistryCreateDto) throws DaoCheckedException {

        Long companyId = driverRegistryCreateDto.getCompanyId();
        Long driverId = driverRegistryCreateDto.getDriverId();

        Optional<TaxiCompany> companyResp = taxiCompanyDao.findById(companyId);
        Optional<Driver> driverResp = driverDao.findById(driverId);

        if (driverResp.isEmpty()) throw new DaoException("Can't find driver with id " + driverId);
        if (companyResp.isEmpty()) throw new DaoException("Can't find taxi company with id " + companyId);

        DriverRegistry dr = modelMapper.map(driverRegistryCreateDto, DriverRegistry.class);
        dr.setDriver(driverResp.get());
        dr.setTaxiCompany(companyResp.get());
        dr.setRegistrationDate(LocalDate.now());

        return abstractDao.create(dr);
    }

    @Override
    public List<DriverRegistry> findAllEntries() {
        return driverRegistryDao.getAll();
    }

    @Override
    public List<DriverRegistry> findDriverCompanies(Long driverId) {
        return driverRegistryDao.getByDriverId(driverId);
    }

    @Override
    @Transactional
    public DriverRegistry updateRegistration(DriverRegistryUpdateDto dto){
        DriverRegistry entry = driverRegistryDao.getEntry(dto.getDriverId(), dto.getCompanyId());
        modelMapper.map(dto, entry);
        return abstractDao.update(entry);
    }
}
