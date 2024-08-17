package com.senla.food.normal.services.implementations.price;

import com.senla.food.normal.services.PriceService;

//@Component
public class ExpensivePriceServiceImpl implements PriceService {
    @Override
    public String getPriceResult() {
        return "Wooh most luxury goods and high prices from: " + this.getClass().getSimpleName();
    }
}
