package com.senla.dao.customerrating;

import com.senla.models.customerrating.CustomerRating;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class CustomerRatingDao extends AbstractLongDao<CustomerRating> implements ICustomerRatingDao {

    @Override
    @PostConstruct
    protected void init() {
        super.clazz = CustomerRating.class;
    }

    @Override
    public CustomerRating getCustomerRatingWithRide(Long ratingId, Long customerId) {
        TypedQuery<CustomerRating> q = em.createQuery("""
                                            select cr from CustomerRating cr
                                            join fetch cr.ride r
                                     
                                            where cr.id = :ratingId
                                                and r.customer.id = :customerId
                                            """, CustomerRating.class);
        q.setParameter("ratingId", ratingId);
        q.setParameter("customerId", customerId);
        return q.getSingleResult();
    }

//    join fetch r.customer c
}

