package com.senla;

import com.senla.controller.UserController;
import com.senla.ctx.ApplicationContext;

public class Main {
    public static void main(String[] args) {

        var ctx = new ApplicationContext("com.senla");
        ctx.init();
        UserController controller = (UserController) ctx.getComponent(UserController.class);
        controller.execute();
    }
}