package com.senla.service;


import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.annotations.PostConstruct;
import com.senla.dao.UserDao;
import com.senla.model.User;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @PostConstruct
    private void someAuxInfo(){
        System.out.println("PostConstruct Called in: " + this.getClass().getSimpleName());
    }

    @Autowire
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

}
