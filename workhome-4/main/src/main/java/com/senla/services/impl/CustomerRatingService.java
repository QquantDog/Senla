package com.senla.services.impl;

import com.senla.models.customerrating.CustomerRating;
import com.senla.repositories.impl.CustomerRatingRepository;
import com.senla.services.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerRatingService extends AbstractLongIdGenericService<CustomerRating> {
    @Autowired
    public CustomerRatingService(CustomerRatingRepository customerRatingRepository) {
        this.abstractRepository = customerRatingRepository;
    }
}
