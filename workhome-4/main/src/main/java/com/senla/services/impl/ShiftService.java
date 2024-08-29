package com.senla.services.impl;

import com.senla.models.shift.Shift;
import com.senla.repositories.impl.ShiftRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class ShiftService extends AbstractLongIdGenericService<Shift> {
    @Autowired
    public ShiftService(ShiftRepository repository) {
        this.abstractRepository = repository;
    }
}
