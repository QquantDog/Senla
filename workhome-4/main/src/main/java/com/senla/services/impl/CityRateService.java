package com.senla.services.impl;

import com.senla.models.cityrate.CityRate;
import com.senla.repositories.impl.CityRateRepository;
import com.senla.repositories.impl.CityRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityRateService extends AbstractLongIdGenericService<CityRate> {
    @Autowired
    public CityRateService(CityRateRepository cityRateRepository) {
        this.abstractRepository = cityRateRepository;
    }
}
