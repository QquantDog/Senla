package com.senla.food.normal;


import com.senla.ctx.ApplicationContext;
import com.senla.food.normal.process.OrderPreparation;

public class OrderProcess {
    public static void main(String[] args) {
        var ctx = new ApplicationContext("com.senla.food.normal");
        ctx.init();
        OrderPreparation bean = (OrderPreparation) ctx.getComponent(OrderPreparation.class);
        bean.debug();
    }
}
