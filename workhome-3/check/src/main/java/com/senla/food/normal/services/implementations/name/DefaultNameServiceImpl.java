package com.senla.food.normal.services.implementations.name;

import com.senla.annotations.Component;
import com.senla.food.normal.services.NameService;

@Component
public class DefaultNameServiceImpl implements NameService {

    @Override
    public String getNameResult() {
        return "Service name is default: " + this.getClass().getSimpleName();
    }
}
