package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.util.repository.LongIdRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CityRepository extends LongIdRepository<City> {
    @PostConstruct
    void init(){
        List<City> cities = new ArrayList<>();
        initList(cities);
        super.bulkInit(cities);
    }

    private void initList(List<City> cities) {
        City c1 = new City(1L, "Grodno");
        City c2 = new City(2L, "Minsk");
        cities.add(c1);
        cities.add(c2);
    }

    @Override
    protected void postSaveProcessEntity(City entity) {}
    @Override
    protected void postUpdateProcessEntity(City entity) {}

}
