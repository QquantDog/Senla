package com.senla.food.graphservices;

import com.senla.ctx.ApplicationContext;

public class CycleCheck {
    public static void main(String[] args) {
        var ctx = new ApplicationContext("com.senla.food.graphservices.cycle");
        ctx.init();
    }
}
