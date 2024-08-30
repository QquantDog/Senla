package com.senla.services.impl;

import com.senla.models.address.Address;
import com.senla.repositories.impl.AddressRepository;
import com.senla.services.AddressService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressServiceImpl extends AbstractLongIdGenericService<Address> implements AddressService{
    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.abstractRepository = addressRepository;
    }
}
// Service