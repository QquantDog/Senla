package com.senla.food.normal.services.implementations.food;

import com.senla.food.normal.services.FoodService;

//@Component()
public class SweetFoodServiceImpl implements FoodService {
    @Override
    public String getFoodResult() {
        return "Food: MOSt SWEET candies from " + this.getClass().getSimpleName();
    }
}
