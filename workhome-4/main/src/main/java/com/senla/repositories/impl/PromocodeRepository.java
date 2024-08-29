package com.senla.repositories.impl;

import com.senla.models.city.City;
import com.senla.models.promocode.Promocode;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PromocodeRepository extends AbstractRepository<Promocode, Long> {
    @PostConstruct
    void init(){
        List<Promocode> promocodes = new ArrayList<>();
        initList(promocodes);
        super.bulkInit(promocodes);
    }

    private void initList(List<Promocode> promocodes) {
        Promocode p1 = new Promocode(1L, "DISCOUNT20", BigDecimal.valueOf(0.2), LocalDateTime.now(), LocalDateTime.of(2027, 4, 5, 6, 8),"Discount 20%");
        Promocode p2 = new Promocode(2L, "DISCOUNT30", BigDecimal.valueOf(0.3), LocalDateTime.now(), LocalDateTime.of(2025, 4, 5, 6, 8),"Discount 30%");
        promocodes.add(p1);
        promocodes.add(p2);
    }

    @Override
    protected void postSaveProcessEntity(Promocode entity) {}
    @Override
    protected void postUpdateProcessEntity(Promocode entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
