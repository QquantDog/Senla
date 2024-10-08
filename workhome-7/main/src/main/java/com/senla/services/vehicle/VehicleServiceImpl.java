package com.senla.services.vehicle;

import com.senla.dao.ratetier.RateTierDao;
import com.senla.dao.vehicle.VehicleDao;
import com.senla.dao.vehiclebrand.VehicleBrandDao;
import com.senla.dto.vehicle.VehicleCreateDto;
import com.senla.dto.vehicle.VehicleUpdateDto;
import com.senla.exceptions.DaoException;
import com.senla.models.ratetier.RateTier;
import com.senla.models.vehicle.Vehicle;
import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class VehicleServiceImpl extends AbstractLongIdGenericService<Vehicle> implements VehicleService {

    @Autowired
    private VehicleDao vehicleDao;

    @Autowired
    private VehicleBrandDao vehicleBrandDao;

    @Autowired
    private RateTierDao rateTierDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @PostConstruct
    protected void init() {
        super.abstractDao = vehicleDao;
    }

    @Override
    @Transactional
    public Vehicle createVehicle(VehicleCreateDto dto) {
        Optional<VehicleBrand> brandResp = vehicleBrandDao.findById(dto.getBrandId());
        if(brandResp.isEmpty()) throw new DaoException("Brand not found");

        Optional<RateTier> rateResp = rateTierDao.findById(dto.getTierId());
        if(rateResp.isEmpty()) throw new DaoException("Rate not found");

        VehicleBrand brand = brandResp.get();
        RateTier rateTier = rateResp.get();

        Vehicle vehicle = modelMapper.map(dto, Vehicle.class);
        vehicle.setBrand(brand);
        vehicle.setRateTier(rateTier);

        abstractDao.create(vehicle);

        return vehicle;
    }

    @Override
    @Transactional
    public Vehicle updateVehicle(Long id, VehicleUpdateDto dto) {
        Optional<Vehicle> resp = abstractDao.findById(id);
        if (resp.isEmpty()) throw new DaoException("Can't find entity with id " + id);

        Vehicle vehicleToUpdate =  resp.get();
        modelMapper.map(dto, vehicleToUpdate);

        if(!vehicleToUpdate.getBrand().getBrandId().equals(dto.getBrandId())) {
            Optional<VehicleBrand> brandResp = vehicleBrandDao.findById(dto.getBrandId());
            brandResp.ifPresentOrElse(vehicleToUpdate::setBrand, ()->{throw new DaoException("Brand not found");});
        }
        if(!vehicleToUpdate.getRateTier().getTierId().equals(dto.getTierId())) {
            Optional<RateTier> rateResp = rateTierDao.findById(dto.getTierId());
            rateResp.ifPresentOrElse(vehicleToUpdate::setRateTier, ()->{throw new DaoException("Rate not found");});
        }
        return abstractDao.update(vehicleToUpdate);

    }
}
