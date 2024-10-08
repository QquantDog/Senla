package com.senla.dao.shift;

import com.senla.models.shift.Shift;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ShiftDao extends AbstractLongDao<Shift> implements IShiftDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Shift.class;
    }

    @Override
    public List<Shift> findAllByJPQL(){
        TypedQuery<Shift> q = em.createQuery("select s from Shift s", Shift.class);
        return q.getResultList();
    }

//    @Override
//    public List<Shift> getAllShifts

    @Override
    public Optional<Shift> findByIdJPQL(Long id){
        TypedQuery<Shift> q = em.createQuery("select s from Shift s where s.shiftId = :id", Shift.class);
        q.setParameter("id", id);
        return q.getResultList().stream().findFirst();
    }

//    @Override
//    public List<Shift> findShiftsWithinCityAndStatuses(String cityName, List<RideStatus> statuses){
//        Query q = em.createQuery("""
//            select distinct s from Shift s
//                join s.rides r
//                join s.cab c
//                join s.driver d
//                join c.city ct
//                where s.endtime is not null
//                        and ct.cityName ILIKE :cityName
//                        and r.status in (:statuses)
//            """);
//        q.setParameter("cityName", cityName);
//        q.setParameter("statuses", statuses);
//        return q.getResultList();
//    }


//    найти все незакрытые сессии водителей которые больше 24 часов
//    просто найти незавершенные сессии водителей
//    нельзя прибавить интервал к таймстампу в jpql)))))))))
    @Override
    public List<Shift> findOpenShifts(){
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
                where s.endtime is null
            """, Shift.class);
        return q.getResultList();
    }

    @Override
    public List<Shift> findShiftBySpecification(Specification<Shift> shiftSpecification){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Shift> cq = cb.createQuery(Shift.class);
        Root<Shift> root = cq.from(Shift.class);

        cq.select(root);
        if(shiftSpecification != null){
            cq.where(shiftSpecification.toPredicate(root, cq , cb));
        }
        TypedQuery<Shift> query = em.createQuery(cq);

        return query.getResultList();
    }

    @Override
    public Tuple findShiftDriverCab() {
        return null;
    }

    @Override
    public List<Shift> getFullResponse() {
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
            join fetch s.cab cab
            join fetch s.driver d
            join fetch s.rate r
            """, Shift.class);
        return q.getResultList();
    }
    @Override
    public Shift getSingleResponse(Long shiftId) {
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
            join fetch s.cab cab
            join fetch s.driver d
            join fetch s.rate r
            where s.shiftId = :shiftId
            """, Shift.class);
        q.setParameter("shiftId", shiftId);
        return q.getSingleResult();
    }

    @Override
    public List<Shift> getMatchingShifts(Long rateId, Point customerStartPoint, Double radiusThresholdMeters){
        TypedQuery<Shift> q = em.createQuery("""
            select s from Shift s
            join fetch s.driver d
            where s.rate.rateId = :rateId
                and d.isOnShift = true
                and d.isOnRide = false
                and ST_Distance(st_transform(:customerStartPoint, 3857), st_transform(d.currentPoint, 3857)) < :radiusThresholdMeters
                and not exists (select subq from Match subq where subq.shift.id = s.id)
            order by ST_Distance(st_transform(:customerStartPoint, 3857), st_transform(d.currentPoint, 3857)) asc
            """, Shift.class);
        q.setParameter("rateId", rateId);
        q.setParameter("customerStartPoint", customerStartPoint);
        q.setParameter("radiusThresholdMeters", radiusThresholdMeters);
        return q.getResultList();
    }

    public int get5(){
        return 5;
    }
}
