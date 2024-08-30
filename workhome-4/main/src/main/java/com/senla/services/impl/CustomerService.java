package com.senla.services.impl;

import com.senla.models.customer.Customer;
import com.senla.repositories.impl.CustomerRepository;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerService extends AbstractLongIdGenericService<Customer> {
    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.abstractRepository = customerRepository;
    }
}
