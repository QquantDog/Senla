package com.senla.dao.driverregistry;


import com.senla.models.driverregistry.DriverRegistry;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DriverRegistryDao extends AbstractLongDao<DriverRegistry> implements IDriverRegistryDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = DriverRegistry.class;
    }

    @Override
    public List<DriverRegistry> getAll() {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select dr from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          join fetch dr.driver d
                          """, DriverRegistry.class);
        return q.getResultList();
    }

    @Override
    public List<DriverRegistry> getByDriverId(Long driverId) {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select dr from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          where dr.driver.id=:driverId
                          """, DriverRegistry.class);
        q.setParameter("driverId", driverId);
        return q.getResultList();
    }

    @Override
    public DriverRegistry getEntry(Long driverId, Long companyId) {
        TypedQuery<DriverRegistry> q = em.createQuery("""
                          select d
                          from DriverRegistry dr
                          join fetch dr.taxiCompany t
                          join fetch dr.driver d
                          where dr.driver.id=:driverId
                              and dr.taxiCompany.id=:companyId
                          """, DriverRegistry.class);
        q.setParameter("driverId", driverId);
        q.setParameter("companyId", companyId);
        return q.getResultList().getFirst();
    }
}
