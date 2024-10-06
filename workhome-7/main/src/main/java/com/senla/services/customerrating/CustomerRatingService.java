package com.senla.services.customerrating;

import com.senla.dto.customerrating.CustomerRatingCreateDto;
import com.senla.dto.customerrating.CustomerRatingUpdateDto;
import com.senla.models.customerrating.CustomerRating;
import com.senla.util.service.GenericService;

public interface CustomerRatingService extends GenericService<CustomerRating, Long> {
    CustomerRating createCustomerRate(CustomerRatingCreateDto customerRatingCreateDto);
    CustomerRating updateCustomerRate(CustomerRatingUpdateDto customerRatingUpdateDto);
}
