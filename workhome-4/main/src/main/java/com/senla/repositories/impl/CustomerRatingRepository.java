package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.models.customerrating.CustomerRating;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRatingRepository extends AbstractRepository<CustomerRating, Long> {
    @PostConstruct
    void init(){
        List<CustomerRating> ratings = new ArrayList<>();
        initList(ratings);
        super.bulkInit(ratings);
    }

    private void initList(List<CustomerRating> ratings) {
        CustomerRating rating1 = new CustomerRating(1L, BigDecimal.valueOf(4.9), LocalDateTime.of(2020,4,5,6,7), "Nice poezdka", 1L);
        ratings.add(rating1);
    }

//    private Long ratingId;
//    private BigDecimal rating;
//    private LocalDateTime createdAt;
//    private String comment;
//
//    private Long rideId;

    @Override
    protected void postSaveProcessEntity(CustomerRating entity) {}
    @Override
    protected void postUpdateProcessEntity(CustomerRating entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
