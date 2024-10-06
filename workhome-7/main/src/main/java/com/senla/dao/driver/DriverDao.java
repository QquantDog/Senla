package com.senla.dao.driver;

import com.senla.models.driver.Driver;
import com.senla.models.driver.Driver_;
import com.senla.models.ride.Ride;
import com.senla.models.taxicompany.TaxiCompany_;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DriverDao extends AbstractLongDao<Driver> implements IDriverDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Driver.class;
    }

    @Override
    public Driver bindToUser(Long userId){

        return null;
    }
    @Override
    public Driver getDriverWithCompanies(Long driverId){
        TypedQuery<Driver> q = em.createQuery("""
            select d from Driver d
            left join fetch d.registrationEntries re
                where d.id = :driverId
            """, Driver.class);
        q.setParameter("driverId", driverId);
        return q.getResultList().getFirst();
    }

    @Override
    public Tuple findDriverCabWithinCompanies(Long driverId, Long cabId) {
        TypedQuery<Tuple> q = em.createQuery("""
            select d, c from Driver d
            join d.registrationEntries re
            join re.taxiCompany t
            join t.cabs c
            where d.id = :driverId
                and c.id = :cabId
            """, Tuple.class);
//        можно добавить условие сюда - но лучше пусть сервис нагенерит конкретных эксепшнов
//        and c.isOnShift = false and d.isOnShift = false
        q.setParameter("driverId", driverId);
        q.setParameter("cabId", cabId);
        return q.getSingleResult();
    }
}
