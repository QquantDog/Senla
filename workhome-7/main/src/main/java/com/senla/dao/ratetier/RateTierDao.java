package com.senla.dao.ratetier;

import com.senla.models.ratetier.RateTier;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class RateTierDao extends AbstractLongDao<RateTier> implements IRateTierDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = RateTier.class;
    }

    @Override
    public RateTier findTierForCab(Long cabId) {
        TypedQuery<RateTier> q = em.createQuery( """
                                         select r from RateTier r
                                         join r.vehicles v
                                         join v.cabs c
                                         where c.cabId=:cabId
                                         """ , RateTier.class);
        q.setParameter("cabId", cabId);
        return q.getSingleResult();
    }
}
