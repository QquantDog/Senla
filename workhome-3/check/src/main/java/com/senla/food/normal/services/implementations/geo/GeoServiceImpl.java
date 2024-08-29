package com.senla.food.normal.services.implementations.geo;

import com.senla.annotations.Component;
import com.senla.food.normal.services.GeoService;

@Component
public class GeoServiceImpl implements GeoService {

    @Override
    public String getGeo() {
        return "longitude: 34.123, latitude: 43.563";
    }
}
