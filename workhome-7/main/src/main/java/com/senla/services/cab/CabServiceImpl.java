package com.senla.services.cab;

import com.senla.dao.cab.CabDao;
import com.senla.dao.taxicompany.TaxiCompanyDao;
import com.senla.dao.vehicle.VehicleDao;
import com.senla.dto.cab.CabCreateDto;
import com.senla.dto.cab.CabUpdateDto;
import com.senla.exceptions.DaoException;
import com.senla.models.cab.Cab;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.models.vehicle.Vehicle;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class CabServiceImpl extends AbstractLongIdGenericService<Cab> implements CabService {
    @Autowired
    private CabDao cabDao;
    @Autowired
    private VehicleDao vehicleDao;
    @Autowired
    private TaxiCompanyDao taxiCompanyDao;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    public void init() {
        super.abstractDao = cabDao;
    }


    @Override
    @Transactional()
    public Cab createCab(CabCreateDto dto) {
        Optional<Vehicle> vehicleResp = vehicleDao.findById(dto.getVehicleId());
        if(vehicleResp.isEmpty()) throw new DaoException("Vehicle not found");

        Optional<TaxiCompany> taxiCompanyResp = taxiCompanyDao.findById(dto.getTaxiCompanyId());
        if(taxiCompanyResp.isEmpty()) throw new DaoException("Taxi company not found");

        Vehicle vehicle = vehicleResp.get();
        TaxiCompany taxiCompany = taxiCompanyResp.get();

        Cab cab = modelMapper.map(dto, Cab.class);
        cab.setIsOnShift(false);
        cab.setVehicle(vehicle);
        cab.setCompany(taxiCompany);

        return abstractDao.create(cab);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Cab updateCab(Long id, CabUpdateDto dto) {
        Optional<Cab> cabResp = abstractDao.findById(id);
        if (cabResp.isEmpty()) throw new DaoException("Can't find entity with id " + id);

        Cab cabToUpdate = cabResp.get();
//        post/put НЕ ДОЛЖНЫ менять активен или нет драйвер
//        это специальный эдпоинт для изменения ТОЛЬКО этого параметра - и участия такси в подборе
        Boolean isOnShiftSaved = cabToUpdate.getIsOnShift();
        modelMapper.map(dto, cabToUpdate);
        cabToUpdate.setIsOnShift(isOnShiftSaved);

        if(!cabToUpdate.getVehicle().getVehicleId().equals(dto.getVehicleId())) {
            Optional<Vehicle> vehicleResp = vehicleDao.findById(dto.getVehicleId());
            vehicleResp.ifPresentOrElse(cabToUpdate::setVehicle, ()->{throw new DaoException("Vehicle not found");});
        }
        if(!cabToUpdate.getCompany().getCompanyId().equals(dto.getTaxiCompanyId())) {
            Optional<TaxiCompany> taxiCompanyResp = taxiCompanyDao.findById(dto.getTaxiCompanyId());
            taxiCompanyResp.ifPresentOrElse(cabToUpdate::setCompany, ()->{throw new DaoException("Taxi company not found");});
        }
        return abstractDao.update(cabToUpdate);
    }

    @Override
    public List<Cab> getAll() {
        return cabDao.getAllCabs();
    }
}
