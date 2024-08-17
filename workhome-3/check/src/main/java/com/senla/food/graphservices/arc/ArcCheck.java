package com.senla.food.graphservices.arc;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.food.normal.services.FoodService;

@Component
public class ArcCheck {
    private Arc arcCheckNode;
    private PastaService pastaService;

    @Autowire
    private ArcCheck(Arc arcCheckNode, PastaService pastaService){
        this.arcCheckNode = arcCheckNode;
        this.pastaService = pastaService;
    }
}