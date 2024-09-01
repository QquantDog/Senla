package com.senla.services.impl;

import com.senla.models.customer.Customer;
import com.senla.repositories.impl.CustomerRepository;
import com.senla.services.CustomerService;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerServiceImpl extends AbstractLongIdGenericService<Customer> implements CustomerService {
    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.abstractRepository = customerRepository;
    }
}
