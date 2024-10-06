package com.senla.dao.ride;


import com.senla.models.ride.Ride;

import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityGraph;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class RideDao extends AbstractLongDao<Ride> implements IRideDao {

    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Ride.class;
    }

//    get All Data with completed and without promocode
    @Override
    public List<Ride> findBySpecification(Specification<Ride> rideSpecification) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ride> cq = cb.createQuery(Ride.class);
        Root<Ride> root = cq.from(Ride.class);

        cq.select(root);
        if(rideSpecification != null){
            cq.where(rideSpecification.toPredicate(root, cq , cb));
        }
        TypedQuery<Ride> query = em.createQuery(cq);

        return query.getResultList();
    }

//    @Override
//    public List<Tuple> findRidesWithAvgDistanceMoreThan(BigDecimal distance) {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
//        Root<Ride> root = cq.from(Ride.class);
//
//        Join<Ride, Rate> rideRatesJoin = root.join(Ride_.currentRate);
//        Join<Rate, City> cityRateCityJoin = rideRatesJoin.join(CityRate_.city);
//
//        cq.multiselect(root, cityRateCityJoin.get(City_.cityName), cb.avg(root.get(Ride_.rideDistanceMeters)), cb.count(root.get(Ride_.rideDistanceMeters)));
//        cq.groupBy(cityRateCityJoin.get(City_.cityName));
//        cq.having(cb.ge(cb.avg(root.get(Ride_.rideDistanceMeters)), distance));
//        cq.orderBy(cb.asc(cityRateCityJoin.get(City_.cityName)));
//        TypedQuery<Tuple> query = em.createQuery(cq);
//
//        return query.getResultList();
//    }
//    @Override
//    public List<Tuple> fullJoin(){
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
//        Root<Ride> root = cq.from(Ride.class);
//
//        Join<Ride, User> customerJoin = root.join(Ride_.user);
//        Join<Ride, Shift> shiftJoin = root.join(Ride_.shift);
//        Join<Ride, Rate> cityRateJoin = root.join(Ride_.currentRate);
//        Join<Ride, Promocode> promocodeJoin = root.join(Ride_.promocode);
//        Join<Ride, CustomerRating> customerRatingJoin = root.join(Ride_.customerRatings);
//
//        cq.multiselect(root, customerJoin, shiftJoin, cityRateJoin, promocodeJoin);
//        TypedQuery<Tuple> query = em.createQuery(cq);
//        return query.getResultList();
//    }
//
////    The Criteria API supports the same features as a JPQL query.
////    So, you can use a subquery only in your WHERE but not in the SELECT or FROM clause.
//    @Override
//    public List<Ride> findLowestAVGPerDistance() {
//        CriteriaBuilder cb = em.getCriteriaBuilder();
//        CriteriaQuery<Ride> cq = cb.createQuery(Ride.class);
//        Root<Ride> root = cq.from(Ride.class);
//
//        Subquery<Double> sub = cq.subquery(Double.class);
//        Root<Ride> subRoot = sub.from(Ride.class);
////        найти в подзапросе среднее
////        sub.select(cb.avg(subRoot.get(Ride_.rideDistanceMeters)));
//        Expression<Double> medianDistanceThresholdExpression = cb.function(
//                "AVG", Double.class ,subRoot.get(Ride_.rideDistanceMeters)
//        );
//        sub.select(medianDistanceThresholdExpression);
//
//        cq.where(cb.le(root.get(Ride_.rideDistanceMeters) ,sub));
//        cq.select(root);
//        TypedQuery<Ride> query = em.createQuery(cq);
//        return query.getResultList();
//    }

    @Override
    public Optional<Ride> getGraph1(){
        EntityGraph<Ride> g = em.createEntityGraph(Ride.class);
//        SubGraph<CustomerRating> s1 = g.addSubgraph("fq");
//        s1.addAttributeNodes("customerRatings");
        g.addAttributeNodes("shift");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", g);

        return Optional.of(em.find(Ride.class, 2, properties));
    }

    @Override
    public Optional<Ride> getGraph2(){
        EntityGraph entityGraph = em.getEntityGraph("rides-with-shifts-and-cabs-and-customers");
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.fetchgraph", entityGraph);
        return Optional.of(em.find(Ride.class, 2, properties));
    }

    @Override
    public Ride getSpecificRideAndCheckCustomer(Long rideId, Long customerId) {
        TypedQuery<Ride> q = em.createQuery("""
                                            select r from Ride r
                                            where r.id = :rideId
                                                and r.customer.id = :customerId
                                            """, Ride.class);
        q.setParameter("rideId", rideId);
        q.setParameter("customerId", customerId);
        return q.getSingleResult();
    }

    @Override
    public List<Ride> getFullRides() {
        TypedQuery<Ride> q = em.createQuery("""
                                            select r from Ride r
                                            left join fetch r.customer
                                            left join fetch r.shift
                                            left join fetch r.customerRating
                                            left join fetch r.payment
                                            left join fetch r.promocode
                                            left join fetch r.rate
                                            """, Ride.class);
        return q.getResultList();
    }

    @Override
    public List<Ride> findActiveRide(Long customerId){
        TypedQuery<Ride> q = em.createQuery("""
                                            select r from Ride r
                                            where r.rideEndTime is null
                                                and r.customer.id = :customerId
                                            """, Ride.class);
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }

    @Override
    public Double getMinimalCartesianDistance(Point startPoint, Point endPoint) {
        TypedQuery<Double> q = em.createQuery("""
                                            select st_distance( st_transform(:startPoint, 3857), st_transform(:endPoint, 3857))
                                            """, Double.class);
        q.setParameter("startPoint", startPoint);
        q.setParameter("endPoint", endPoint);
        return q.getSingleResult();
    }

    @Override
    public Tuple matchRideAndShift(Long rideId, Long shiftId) {
//        селект выдаст 0 строк, если в таблице matches нет соответствующего поля
        TypedQuery<Tuple> q = em.createQuery("""
                        select r, s from Ride r
                        join fetch r.matches m
                        join fetch m.shift s
                        join fetch s.driver
                        where r.id = :rideId
                            and s.id = :shiftId
                        """, Tuple.class);
        q.setParameter("rideId", rideId);
        q.setParameter("shiftId", shiftId);
        return q.getSingleResult();
    }

    @Override
    public Ride verifyRideByDriver(Long rideId, Long driverId){
        TypedQuery<Ride> q = em.createQuery("""
                        select r from Ride r
                        join fetch r.shift s
                        join fetch s.driver d
                        where r.id = :rideId
                            and r.rideEndTime is null
                            and d.id = :driverId
                        """, Ride.class);
        q.setParameter("rideId", rideId);
        q.setParameter("driverId", driverId);
        return q.getSingleResult();
    }
    @Override
    public Ride verifyRideByCustomer(Long rideId, Long customerId){
        TypedQuery<Ride> q = em.createQuery("""
                        select r from Ride r
                        join r.customer c
                        where r.id = :rideId
                            and r.customer.id = :customerId
                        """, Ride.class);
        q.setParameter("rideId", rideId);
        q.setParameter("customerId", customerId);
        return q.getSingleResult();
    }

    @Override
    public Ride getRideWithPromocode(Long rideId) {
        TypedQuery<Ride> q = em.createQuery("""
                        select r from Ride r
                        join fetch r.promocode p
                        where r.id = :rideId
                        """, Ride.class);
        q.setParameter("rideId", rideId);
        return q.getSingleResult();
    }
}