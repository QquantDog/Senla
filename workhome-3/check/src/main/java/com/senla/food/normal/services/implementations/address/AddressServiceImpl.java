package com.senla.food.normal.services.implementations.address;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.food.normal.services.AddressService;
import com.senla.food.normal.services.GeoService;

@Component
public class AddressServiceImpl implements AddressService {

    @Autowire
    private GeoService geoService;

    @Override
    public String getAddress() {
        return "street central ";
    }

    @Override
    public String getFullAddress() {
        return getAddress() + geoService.getGeo();
    }
}
