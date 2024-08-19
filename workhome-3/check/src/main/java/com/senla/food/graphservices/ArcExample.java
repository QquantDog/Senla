package com.senla.food.graphservices;

import com.senla.ctx.ApplicationContext;
import com.senla.food.graphservices.arc.ArcCheck;

public class ArcExample {
    public static void main(String[] args) {
        var ctx = new ApplicationContext("com.senla.food.graphservices.arc");
        ctx.init();
        ArcCheck a = (ArcCheck) ctx.getComponent(ArcCheck.class);
    }
}
