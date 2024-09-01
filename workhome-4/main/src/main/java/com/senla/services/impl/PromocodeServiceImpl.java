package com.senla.services.impl;

import com.senla.models.promocode.Promocode;
import com.senla.repositories.impl.PromocodeRepository;
import com.senla.services.PromocodeService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromocodeServiceImpl extends AbstractLongIdGenericService<Promocode> implements PromocodeService {
    @Autowired
    public PromocodeServiceImpl(PromocodeRepository promocodeRepository) {
        this.abstractRepository = promocodeRepository;
    }
}
