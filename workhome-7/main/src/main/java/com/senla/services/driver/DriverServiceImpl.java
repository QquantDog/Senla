package com.senla.services.driver;

import com.senla.dao.driver.DriverDao;
import com.senla.dao.driverregistry.DriverRegistryDao;
import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.exceptions.DaoCheckedException;
import com.senla.exceptions.DaoException;
import com.senla.models.driver.Driver;
import com.senla.models.driverregistry.DriverRegistry;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DriverServiceImpl extends AbstractLongIdGenericService<Driver> implements DriverService {
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private TaxiCompanyDao taxiCompanyDao;
    @Autowired
    private DriverRegistryDao driverRegistryDao;


    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = driverDao;
    }

    @Override
    @Transactional
    public Driver updateDriver(Long id, Driver driver) throws DaoCheckedException {
        Optional<Driver> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);
        else {
            Long driverId = resp.get().getId();
            driver.setId(driverId);
            abstractDao.update(driver);
//            Driver driver = resp.get();
//            modelMapper.map(promocodeUpdateDto, promocode);
//            abstractDao.update(promocode);
//         
            return driver;
        }
    }

}
