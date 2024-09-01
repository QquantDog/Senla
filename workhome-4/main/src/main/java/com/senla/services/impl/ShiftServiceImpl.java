package com.senla.services.impl;

import com.senla.models.shift.Shift;
import com.senla.repositories.impl.ShiftRepository;
import com.senla.services.ShiftService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiftServiceImpl extends AbstractLongIdGenericService<Shift> implements ShiftService {
    @Autowired
    public ShiftServiceImpl(ShiftRepository repository) {
        this.abstractRepository = repository;
    }
}
