package com.senla.dao;

import com.senla.annotations.Autowire;
import com.senla.annotations.Component;
import com.senla.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao{

    private final List<User> data =
            new ArrayList<User>(){{add(new User("qq", 1)); add(new User("ww", 2));}};

    @Override
    public List<User> getAll() {
        return data;
    }
}
