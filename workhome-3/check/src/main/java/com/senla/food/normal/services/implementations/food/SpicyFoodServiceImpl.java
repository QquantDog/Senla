package com.senla.food.normal.services.implementations.food;

import com.senla.annotations.Component;
import com.senla.food.normal.services.FoodService;

//@Component
public class SpicyFoodServiceImpl implements FoodService {
    @Override
    public String getFoodResult() {
        return "Food: Ahhh SPiCY noddles from " + this.getClass().getSimpleName();
    }
}
