package com.senla.food.graphservices.arc;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.food.normal.services.FoodService;

@Component
public class ArcImpl implements Arc{
    private PastaService pastaService;

    @Autowire
    private ArcImpl(PastaService pastaService){
        this.pastaService = pastaService;
    }
}
