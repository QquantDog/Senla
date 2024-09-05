package com.senla.services.impl;

import com.senla.models.user.User;
import com.senla.repositories.jdbc.UserJDBCRepo;
import com.senla.services.UserService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class UserServiceImpl extends AbstractLongIdGenericService<User> implements UserService {
    @Autowired
    public UserServiceImpl(UserJDBCRepo userRepository) {
        this.abstractRepository = userRepository;
    }
}
