package com.senla.services.impl;

import com.senla.models.promocode.Promocode;
import com.senla.repositories.impl.PromocodeRepository;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromocodeService extends AbstractLongIdGenericService<Promocode> {
    @Autowired
    public PromocodeService(PromocodeRepository promocodeRepository) {
        this.abstractRepository = promocodeRepository;
    }
}
