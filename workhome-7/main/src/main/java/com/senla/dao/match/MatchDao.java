package com.senla.dao.match;

import com.senla.models.match.Match;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

@Component
public class MatchDao extends AbstractLongDao<Match> implements IMatchDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Match.class;
    }

    @Override
    public void clearRideEntriesOnAccept(Long shiftId) {
        Query q = em.createQuery("""
                DELETE FROM Match m
                where m.ride.id =
                    (select m.ride.id from Match m
                        where m.shift.id = :shiftId)""");
        q.setParameter("shiftId", shiftId);
        q.executeUpdate();
    }


    @Override
    public void clearSpecificMatch(Long shiftId) {
        Query q = em.createQuery("""
                DELETE FROM Match m
                where m.shift.id = :shiftId""");
        q.setParameter("shiftId", shiftId);
        q.executeUpdate();
    }

    @Override
    public Match findMatchByRideAndShift(Long rideId, Long shiftId) {
        TypedQuery<Match> q = em.createQuery("""
                select m from Match m
                where m.shift.id = :shiftId
                    and m.ride.id = :rideId""", Match.class);
        q.setParameter("shiftId", shiftId);
        return q.getSingleResult();
    }

    @Override
    public Match matchRideAndShift(Long rideId, Long shiftId) {
//        селект выдаст 0 строк, если в таблице matches нет соответствующего поля
        TypedQuery<Match> q = em.createQuery("""
                        select m from Match m
                        join m.ride r
                        join m.shift s
                        where r.id = :rideId
                            and s.id = :shiftId
                        """, Match.class);
        q.setParameter("rideId", rideId);
        q.setParameter("shiftId", shiftId);
        return q.getSingleResult();
    }
}
