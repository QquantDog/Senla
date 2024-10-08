package com.senla.dao.taxicompany;

import com.senla.models.taxicompany.TaxiCompany;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaxiCompanyDao extends AbstractLongDao<TaxiCompany> implements ITaxiCompanyDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = TaxiCompany.class;
    }

    @Override
    public List<TaxiCompany> getAllWithCabs() {
        TypedQuery<TaxiCompany> q = em.createQuery("""
                SELECT t FROM TaxiCompany t
                join fetch t.cabs c
                join fetch c.vehicle v
                join fetch v.brand""", TaxiCompany.class);
        return q.getResultList();
    }
}