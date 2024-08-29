package com.senla.food.graphservices.cycle;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;

@Component
public class CycleNode2Impl implements CycleNode2 {
    private CycleCheck cycleCheck;

    @Autowire
    private CycleNode2Impl(CycleCheck cycleCheck){
        this.cycleCheck = cycleCheck;
    }
}
