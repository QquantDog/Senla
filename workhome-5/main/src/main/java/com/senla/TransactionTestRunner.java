package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.config.SpringConfig;
import com.senla.controllers.RideController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TransactionTestRunner {
    public static void main(String[] args) throws JsonProcessingException {
        var ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        RideController c = ctx.getBean(RideController.class);

        c.transactionTestCall();
    }

}
