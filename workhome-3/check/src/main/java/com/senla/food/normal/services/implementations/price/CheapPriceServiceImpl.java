package com.senla.food.normal.services.implementations.price;

import com.senla.annotations.Component;
import com.senla.food.normal.services.PriceService;

@Component
public class CheapPriceServiceImpl implements PriceService {
    @Override
    public String getPriceResult() {
        return "Cheapest prices from: " + this.getClass().getSimpleName();
    }
}
