package com.senla.food.normal.services.implementations.discounts;

import com.senla.annotations.Component;
import com.senla.food.normal.services.DiscountService;

@Component
public class DiscountServiceImpl1 implements DiscountService {
    @Override
    public String getDiscount() {
        return "middle discount " + this.getClass().getSimpleName();
    }
}
