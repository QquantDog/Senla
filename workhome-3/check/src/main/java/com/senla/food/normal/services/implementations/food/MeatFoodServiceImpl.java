package com.senla.food.normal.services.implementations.food;

import com.senla.annotations.Component;
import com.senla.food.normal.services.FoodService;

@Component
public class MeatFoodServiceImpl implements FoodService {
    @Override
    public String getFoodResult() {
        return "Food: Meat food from " + this.getClass().getSimpleName();
    }
}
