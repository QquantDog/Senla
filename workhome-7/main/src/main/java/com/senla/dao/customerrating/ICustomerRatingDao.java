package com.senla.dao.customerrating;

import com.senla.models.customerrating.CustomerRating;
import com.senla.models.driver.Driver;
import com.senla.util.dao.GenericDao;

public interface ICustomerRatingDao extends GenericDao<CustomerRating, Long> {
    CustomerRating getCustomerRatingWithRide(Long ratingId, Long customerId);
}
