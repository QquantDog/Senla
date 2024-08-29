package com.senla.food.graphservices.arc;

import com.senla.annotations.Component;

@Component
public class PastaServiceImpl implements PastaService {
    @Override
    public String getPastaResult() {
        return "Food: Meat food from " + this.getClass().getSimpleName();
    }
}
