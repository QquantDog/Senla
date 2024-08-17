package com.senla.food.graphservices.cycle;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;

@Component
public class CycleCheck {
    CycleNode1 cycleNode1;

    @Autowire
    private CycleCheck(CycleNode1 cycleNode1){
        this.cycleNode1 = cycleNode1;
    }
}
