package com.senla.repositories.impl;

import com.senla.models.cab.Cab;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CabRepository extends AbstractRepository<Cab, Long> {
    @PostConstruct
    void init(){
        List<Cab> cabs = new ArrayList<>();
        initList(cabs);
        super.bulkInit(cabs);
    }

    private void initList(List<Cab> cabs) {
        Cab c1 = new Cab(1L, "dfh3432ghv", LocalDate.of(2010, 6, 2), "Yellow", "3452 AA-5", "5423", 1L, 1L);
        Cab c2 = new Cab(2L, "h54j5643gc", LocalDate.of(2009, 3, 7), "Black", "5632 BB-4", "7831", 1L, 2L);
        Cab c3 = new Cab(3L, "duk76l78lv", LocalDate.of(2006, 1, 3), "Red", "1952 MP-7", "6149", 1L, 3L);
        cabs.add(c1);
        cabs.add(c2);
        cabs.add(c3);
    }

    @Override
    protected void postSaveProcessEntity(Cab entity) {}
    @Override
    protected void postUpdateProcessEntity(Cab entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
