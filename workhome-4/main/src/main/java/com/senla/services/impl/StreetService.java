package com.senla.services.impl;


import com.senla.models.street.Street;
import com.senla.repositories.impl.StreetRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StreetService extends AbstractLongIdGenericService<Street> {
    @Autowired
    public StreetService(StreetRepository streetRepository) {
        this.abstractRepository = streetRepository;
    }
}
