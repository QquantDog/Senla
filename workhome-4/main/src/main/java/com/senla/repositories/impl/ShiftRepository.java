package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.models.shift.Shift;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class ShiftRepository extends AbstractRepository<Shift, Long> {
    @PostConstruct
    void init(){
        List<Shift> shifts = new ArrayList<>();
        initList(shifts);
        super.bulkInit(shifts);
    }

    private void initList(List<Shift> shifts) {
        Shift s1 = new Shift(1L, LocalDateTime.of(2020,5,6,2,6), null, 1L, 3L);
        Shift s2 = new Shift(1L, LocalDateTime.of(2024,5,6,2,6), null, 1L, 4L);
        shifts.add(s1);
        shifts.add(s2);
    }

//    private Long shiftId;
//    private Timestamp starttime;
//    private Timestamp endtime;
//
//    private Long cabId;
//    private Long driverId;

    @Override
    protected void postSaveProcessEntity(Shift entity) {}
    @Override
    protected void postUpdateProcessEntity(Shift entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
