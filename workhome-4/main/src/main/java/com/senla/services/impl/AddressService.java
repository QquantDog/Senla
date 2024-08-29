package com.senla.services.impl;

import com.senla.models.address.Address;
import com.senla.repositories.impl.AddressRepository;
import com.senla.repositories.impl.VehicleBrandRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressService extends AbstractLongIdGenericService<Address> {
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.abstractRepository = addressRepository;
    }
}
