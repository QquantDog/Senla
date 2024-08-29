package com.senla.controller;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.service.UserService;

@Component
public class UserController {

    @Autowire
    UserService userService;

    public void execute(){
        userService.getAllUsers().forEach(System.out::println);
    }
}
