package com.senla.services.impl;

import com.senla.models.city.City;
import com.senla.repositories.impl.CityRepository;
import com.senla.services.CityService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityServiceImpl extends AbstractLongIdGenericService<City> implements CityService {
    @Autowired
    public CityServiceImpl(CityRepository cityRepository) {
        this.abstractRepository = cityRepository;
    }
}
