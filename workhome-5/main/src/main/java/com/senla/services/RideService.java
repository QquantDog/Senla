package com.senla.services;

import com.senla.models.ride.Ride;
import com.senla.util.service.GenericService;

public interface RideService extends GenericService<Ride, Long> {
    void transactionSuccessTest();
    void transactionFailureTest();
    void multipleInsertTransactionTest();
    void NONTransactionTest();
    void partialNonTransactionInsertTest();
}
