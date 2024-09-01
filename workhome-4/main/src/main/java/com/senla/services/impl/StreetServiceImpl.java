package com.senla.services.impl;


import com.senla.models.street.Street;
import com.senla.repositories.impl.StreetRepository;
import com.senla.services.StreetService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreetServiceImpl extends AbstractLongIdGenericService<Street> implements StreetService {
    @Autowired
    public StreetServiceImpl(StreetRepository streetRepository) {
        this.abstractRepository = streetRepository;
    }
}
