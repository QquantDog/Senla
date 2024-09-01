package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.config.SpringConfig;
import com.senla.controllers.DriverController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DriverRunner {
    public static void main(String[] args) throws JsonProcessingException {
        var ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        DriverController c = ctx.getBean(DriverController.class);
        testRead(c);
    }
    private static void testRead(DriverController c) throws JsonProcessingException {
        System.out.println(c.getAllDrivers());
        System.out.println(c.getDriverById(1L));
        System.out.println(c.getDriverById(3L));
    }
}
