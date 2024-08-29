package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.models.customer.Customer;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerRepository extends AbstractRepository<Customer, Long> {
    @PostConstruct
    void init(){
        List<Customer> customers = new ArrayList<>();
        initList(customers);
        super.bulkInit(customers);
    }

    private void initList(List<Customer> customers) {
        Customer customer1 = new Customer(1L, "34.53", "25.52", 1L);
        Customer customer2 = new Customer(2L, "35.73", "34.78", null);
        customers.add(customer1);
        customers.add(customer2);
    }

//    private Long customerId;
//    private String currentLat;
//    private String currentLong;
//
//    private Long userId;
//    private Long addressId;

    @Override
    protected void postSaveProcessEntity(Customer entity) {}
    @Override
    protected void postUpdateProcessEntity(Customer entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
