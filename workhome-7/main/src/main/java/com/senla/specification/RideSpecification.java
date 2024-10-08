package com.senla.specification;

import com.senla.models.ride.Ride;
import com.senla.models.ride.Ride_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;


public class RideSpecification {
    public static Specification<Ride> rideTipFrom(BigDecimal from) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.greaterThanOrEqualTo(root.get(Ride_.rideTip), from);
        };
    }
    public static Specification<Ride> rideTipTo(BigDecimal to) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.lessThan(root.get(Ride_.rideTip), to);
        };
    }
    public static Specification<Ride> promocodeEnter(String promocode) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(Ride_.promocodeEnterCode), promocode);
        };
    }
    public static Specification<Ride> latFrom(BigDecimal latFrom) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.greaterThanOrEqualTo(root.get(Ride_.startPointLat), latFrom);
        };
    }
    public static Specification<Ride> latTo(BigDecimal latTo) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.lessThan(root.get(Ride_.startPointLat), latTo);
        };
    }
    public static Specification<Ride> longFrom(BigDecimal longFrom) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.greaterThanOrEqualTo(root.get(Ride_.startPointLat), longFrom);
        };
    }
    public static Specification<Ride> longTo(BigDecimal longTo) {
        return (Root<Ride> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.lessThan(root.get(Ride_.startPointLat), longTo);
        };
    }

    public static Specification<Ride> buildSpecification(BigDecimal rideTipFrom, BigDecimal rideTipTo, String promocodeEnterCode, BigDecimal latFrom, BigDecimal latTo, BigDecimal longFrom, BigDecimal longTo) {
        Specification<Ride> spec = Specification.where(null);
        if (rideTipFrom != null) {
            spec = spec.and(RideSpecification.rideTipFrom(rideTipFrom));
        }
        if (rideTipTo != null) {
            spec = spec.and(RideSpecification.rideTipTo(rideTipTo));
        }
        if (promocodeEnterCode != null) {
            spec = spec.and(RideSpecification.promocodeEnter(promocodeEnterCode));
        }
        if (latFrom != null) {
            spec = spec.and(RideSpecification.latFrom(latFrom));
        }
        if (latTo != null) {
            spec = spec.and(RideSpecification.latTo(latTo));
        }
        if (longFrom != null) {
            spec = spec.and(RideSpecification.longFrom(longFrom));
        }
        if (longTo != null) {
            spec = spec.and(RideSpecification.longTo(longTo));
        }
        return spec;
    }
}
