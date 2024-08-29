package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.models.street.Street;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StreetRepository extends AbstractRepository<Street, Long> {
    @PostConstruct
    void init(){
        List<Street> streets = new ArrayList<>();
        initList(streets);
        super.bulkInit(streets);
    }

    private void initList(List<Street> streets) {
        Street s1 = new Street(1L, "Sovetskay central", 1L);
        Street s2 = new Street(2L, "Rumlevo", 1L);
        Street s3 = new Street(3L, "Minskaya", 2L);
        streets.add(s1);
        streets.add(s2);
        streets.add(s3);
    }

    @Override
    protected void postSaveProcessEntity(Street entity) {}
    @Override
    protected void postUpdateProcessEntity(Street entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
