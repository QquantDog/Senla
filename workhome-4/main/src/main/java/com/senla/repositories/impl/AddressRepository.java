package com.senla.repositories.impl;

import com.senla.models.address.Address;
import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AddressRepository extends AbstractRepository<Address, Long> {
    @PostConstruct
    void init(){
        List<Address> addresses = new ArrayList<>();
        initList(addresses);
        super.bulkInit(addresses);
    }

    private void initList(List<Address> addresses) {
        Address a1 = new Address(1L, "d. 12", "44.22", "55.22", "Central market of Grodno City", 1L);
        Address a2 = new Address(2L, "d. 34", "45.22", "55.12", "Living Complex in the center", 1L);
        Address a3 = new Address(3L, "d. 2", "55.12", "44.25", "Aquapark in Minsk", 2L);
        addresses.add(a1);
        addresses.add(a2);
        addresses.add(a3);
    }

    @Override
    protected void postSaveProcessEntity(Address entity) {}
    @Override
    protected void postUpdateProcessEntity(Address entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
