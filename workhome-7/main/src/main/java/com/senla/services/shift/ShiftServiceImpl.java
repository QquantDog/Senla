package com.senla.services.shift;

import com.senla.dao.city.CityDao;
import com.senla.dao.driver.DriverDao;
import com.senla.dao.rate.RateDao;
import com.senla.dao.ratetier.RateTierDao;
import com.senla.dao.shift.ShiftDao;
import com.senla.dto.shift.ShiftFinishDto;
import com.senla.dto.shift.ShiftStartDto;
import com.senla.exceptions.DaoException;
import com.senla.models.cab.Cab;
import com.senla.models.city.City;
import com.senla.models.driver.Driver;
import com.senla.models.rate.Rate;
import com.senla.models.ratetier.RateTier;
import com.senla.models.ride.RideStatus;
import com.senla.models.shift.Shift;
import com.senla.specification.ShiftSpecification;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Tuple;
import org.locationtech.jts.geom.Point;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ShiftServiceImpl extends AbstractLongIdGenericService<Shift> implements ShiftService {

    @Autowired
    private ShiftDao shiftDao;
    @Autowired
    private DriverDao driverDao;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CityDao cityDao;

    @Autowired
    private RateDao rateDao;
    @Autowired
    private RateTierDao rateTierDao;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = shiftDao;
    }

    @Override
    public void findByIdJPQL(Long userId) {
        shiftDao.findByIdJPQL(userId).ifPresent(System.out::println);
    }

    @Override
    public void findAllByJPQL() {
        shiftDao.findAllByJPQL().forEach(System.out::println);
    }



    //    find average duration per shift
//    fetch average price per shift
    @Override
    public void findShiftBySpecification(LocalDateTime startFrom, LocalDateTime startTo, LocalDateTime endFrom, LocalDateTime endTo, boolean isActive){
        shiftDao.findShiftBySpecification(ShiftSpecification.buildSpecification(startFrom, startTo, endFrom, endTo, isActive)).forEach(System.out::println);
    }

    @Override
    public List<Shift> getShiftFullResponse(){
        return shiftDao.getFullResponse();
    }

    @Override
    @Transactional
    public Shift startShift(ShiftStartDto shiftStartDto) {

        Long cabId = shiftStartDto.getCabId();
        Long driverId = shiftStartDto.getDriverId();

        Long cityId = shiftStartDto.getCityId();

        Optional<City> cityResp = cityDao.findById(cityId);
        if(cityResp.isEmpty()) throw new DaoException("City not found");

        RateTier rt = rateTierDao.findTierForCab(cabId);
        Rate r = rateDao.getRateByTierAndCity(rt.getTierId(), cityId);

        Tuple t;

        try{
            t = driverDao.findDriverCabWithinCompanies(driverId, cabId);
        } catch (NoResultException e){
            throw new DaoException("Matchable cab from relevant taxi company not found");
        } catch (RuntimeException e){
            throw new DaoException("Error matching cab from relevant taxi company");
        }
        Driver driver = t.get(0, Driver.class);
        if(driver.getIsOnShift()) throw new DaoException("Couldn't start shift - driver is already on shift");

        Cab cab = t.get(1, Cab.class);
        if(cab.getIsOnShift()) throw new DaoException("Couldn't start shift - cab is already in use");


        Shift shift = modelMapper.map(shiftStartDto, Shift.class);
        shift.setStarttime(LocalDateTime.now());
        shift.setEndtime(null);
        shift.setCab(cab);
        shift.setDriver(driver);
        shift.setRate(r);
//        shift.setCity(cityResp.get());

        cab.setIsOnShift(true);
        driver.setIsOnShift(true);
        Point pt = shiftStartDto.getShiftStartPoint();

        driver.setCurrentLong(BigDecimal.valueOf(pt.getX()));
//        LAT LONG удалим нахуй
        driver.setCurrentLat (BigDecimal.valueOf(pt.getY()));
        driver.setCurrentPoint(shiftStartDto.getShiftStartPoint());
//        if(1 == 1) throw new DaoException("KCHAAY START SHIFT WITH POINT)))");
//

        return abstractDao.create(shift);
    }

    @Override
    @Transactional
    public Shift finishShift(ShiftFinishDto shiftFinishDto) {
        Long driverId = shiftFinishDto.getDriverId();
        Long shiftId = shiftFinishDto.getShiftId();


        Shift shift = shiftDao.getSingleResponse(shiftId);
        if(shift.getEndtime() != null) throw new DaoException("Shift is already finished");

        Cab cab = shift.getCab();
        Driver driver = shift.getDriver();

        if(!driver.getId().equals(driverId)) throw new DaoException("Driver id mismatch");
        if(!cab.getIsOnShift()) throw new DaoException("Couldn't finish shift - shift is already finished");

        cab.setIsOnShift(false);

        driver.finishShiftAndRestore();

        shift.setEndtime(LocalDateTime.now());

        return abstractDao.update(shift);
//        Optional<>
    }



}
