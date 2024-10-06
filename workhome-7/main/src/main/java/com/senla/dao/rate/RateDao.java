package com.senla.dao.rate;

import com.senla.models.rate.Rate;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class RateDao extends AbstractLongDao<Rate> implements IRateDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Rate.class;
    }

    @Override
    public Rate getRateByTierAndCity(Long tierId, Long cityId) {
        TypedQuery<Rate> q = em.createQuery(
                """
                        select r from Rate r
                        join r.city c
                        join r.rateTier t
                        where c.cityId=:cityId
                            and t.tierId=:tierId
                        """, Rate.class);
        q.setParameter("cityId", cityId);
        q.setParameter("tierId", tierId);
        return q.getSingleResult();
    }
}
