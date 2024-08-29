package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.models.cityrate.CityRate;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CityRateRepository extends AbstractRepository<CityRate, Long>{
    @PostConstruct
    void init(){
        List<CityRate> cityRates = new ArrayList<>();
        initList(cityRates);
        super.bulkInit(cityRates);
    }

    private void initList(List<CityRate> cityRates) {
        CityRate cityRate1 = new CityRate(1L, BigDecimal.valueOf(2.0), BigDecimal.valueOf(1.0), BigDecimal.valueOf(0.4), 120, 1L);
        CityRate cityRate2 = new CityRate(2L, BigDecimal.valueOf(3.0), BigDecimal.valueOf(1.5), BigDecimal.valueOf(0.6), 120, 2L);
        cityRates.add(cityRate1);
        cityRates.add(cityRate2);
    }


    @Override
    protected void postSaveProcessEntity(CityRate entity) {}
    @Override
    protected void postUpdateProcessEntity(CityRate entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
