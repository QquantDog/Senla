package com.senla.service;


import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.dao.UserDao;
import com.senla.model.User;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowire
    public UserServiceImpl(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAll();
    }

}
