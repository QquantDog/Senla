package com.senla.services.rate;

import com.senla.dao.rate.RateDao;
import com.senla.dao.city.CityDao;
import com.senla.dao.ratetier.RateTierDao;
import com.senla.dto.rate.RateCreateDto;
import com.senla.dto.rate.RateUpdateDto;
import com.senla.exceptions.DaoException;
import com.senla.models.city.City;
import com.senla.models.rate.Rate;
import com.senla.models.ratetier.RateTier;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class RateServiceImpl extends AbstractLongIdGenericService<Rate> implements RateService {
    @Autowired
    private RateDao rateDao;
    @Autowired
    private CityDao cityDao;
    @Autowired
    private RateTierDao rateTierDao;

    @Autowired
    private ModelMapper modelMapper;



    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = rateDao;
    }


    @Override
    @Transactional
    public Rate createRate(RateCreateDto dto) {
        Optional<City> cityResp = cityDao.findById(dto.getCityId());
        if(cityResp.isEmpty()) throw new DaoException("City not found");

        Optional<RateTier> tierResp = rateTierDao.findById(dto.getRateTierId());
        if(tierResp.isEmpty()) throw new DaoException("Tier not found");

        City city = cityResp.get();
        RateTier rateTier = tierResp.get();

        Rate rate = modelMapper.map(dto, Rate.class);
        rate.setCity(city);
        rate.setRateTier(rateTier);

        abstractDao.create(rate);

        return rate;
    }

    @Override
    @Transactional
    public Rate updateRate(Long id, RateUpdateDto dto) {
        Optional<Rate> rateResp = abstractDao.findById(id);
        if (rateResp.isEmpty()) throw new DaoException("Can't find entity with id " + id);

        Rate rateToUpdate = rateResp.get();
        modelMapper.map(dto, rateToUpdate);

        if(!rateToUpdate.getCity().getCityId().equals(dto.getCityId())) {
            Optional<City> cityResp = cityDao.findById(dto.getCityId());
            cityResp.ifPresentOrElse(rateToUpdate::setCity, ()->{throw new DaoException("City not found");});
        }
        if(!rateToUpdate.getRateTier().getTierId().equals(dto.getRateTierId())) {
            Optional<RateTier> rateTierResp = rateTierDao.findById(dto.getRateTierId());
            rateTierResp.ifPresentOrElse(rateToUpdate::setRateTier, ()->{throw new DaoException("Rate not found");});
        }
        return abstractDao.update(rateToUpdate);
    }
}
