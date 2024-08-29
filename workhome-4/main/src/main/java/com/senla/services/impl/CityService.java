package com.senla.services.impl;

import com.senla.models.city.City;
import com.senla.repositories.impl.CityRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityService extends AbstractLongIdGenericService<City> {
    @Autowired
    public CityService(CityRepository cityRepository) {
        this.abstractRepository = cityRepository;
    }
}
