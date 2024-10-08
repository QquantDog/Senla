package com.senla.dao.cab;

import com.senla.models.cab.Cab;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CabDao extends AbstractLongDao<Cab> implements ICabDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Cab.class;
    }

    @Override
    public List<Cab> getAllCabs() {
        TypedQuery<Cab> q = em.createQuery("SELECT c FROM Cab c join fetch c.vehicle v join fetch v.brand join fetch c.company", Cab.class);
        return q.getResultList();
    }
}
