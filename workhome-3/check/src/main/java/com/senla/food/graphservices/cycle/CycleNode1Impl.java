package com.senla.food.graphservices.cycle;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;

@Component
public class CycleNode1Impl implements CycleNode1 {
    private CycleNode2 cycleNode2;

    @Autowire
    private CycleNode1Impl(CycleNode2 cycleNode2){
        this.cycleNode2 = cycleNode2;
    }
}
