package com.senla.services.impl;

import com.senla.models.cab.Cab;
import com.senla.repositories.impl.CabRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CabService extends AbstractLongIdGenericService<Cab> {
    @Autowired
    public CabService(CabRepository cabRepository) {
        this.abstractRepository = cabRepository;
    }
}
