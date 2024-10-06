package com.senla.dao.city;

import com.senla.models.city.City;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class CityDao extends AbstractLongDao<City> {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = City.class;
    }
}
