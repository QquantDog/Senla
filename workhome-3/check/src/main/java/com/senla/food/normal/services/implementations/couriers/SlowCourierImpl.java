package com.senla.food.normal.services.implementations.couriers;

import com.senla.annotations.Component;
import com.senla.food.normal.services.CourierService;

@Component
public class SlowCourierImpl implements CourierService {
    @Override
    public String getCourier() {
        return "Slowest Courier " + this.getClass().getSimpleName();
    }
}
