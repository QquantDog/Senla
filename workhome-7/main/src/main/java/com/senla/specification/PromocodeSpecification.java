package com.senla.specification;

import com.senla.models.promocode.Promocode;
import com.senla.models.promocode.Promocode_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PromocodeSpecification {

//  в случае интервала - первая дата всегда существует
    public static Specification<Promocode> startDate(List<LocalDate> startDateInterval) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate startDateFromPredicate = cb.conjunction();
            Predicate startDateToPredicate = cb.conjunction();
//            лишнее условие по идее
            if(!startDateInterval.isEmpty()) startDateFromPredicate = cb.greaterThanOrEqualTo(root.get(Promocode_.startDate), startDateInterval.get(0));
            if(startDateInterval.size() > 1) startDateToPredicate = cb.lessThanOrEqualTo(root.get(Promocode_.startDate), startDateInterval.get(1));
            return cb.and(startDateFromPredicate, startDateToPredicate);
        };
    }

    public static Specification<Promocode> endDate(List<LocalDate> endDateInterval) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate endDateFromPredicate = cb.conjunction();
            Predicate endDateToPredicate = cb.conjunction();
            if(endDateInterval.isEmpty()) endDateFromPredicate = cb.greaterThanOrEqualTo(root.get(Promocode_.endDate), endDateInterval.get(0));
            if(endDateInterval.size() > 1) endDateToPredicate = cb.lessThanOrEqualTo(root.get(Promocode_.endDate), endDateInterval.get(1));
            return cb.and(endDateFromPredicate, endDateToPredicate);
        };
    }

    public static Specification<Promocode> discount(List<BigDecimal> discountInterval) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate discountFromPredicate = cb.conjunction();
            Predicate discountToPredicate = cb.conjunction();
            if(discountInterval.isEmpty()) discountFromPredicate = cb.greaterThanOrEqualTo(root.get(Promocode_.discountValue), discountInterval.get(0));
            if(discountInterval.size() > 1) discountToPredicate   = cb.lessThanOrEqualTo(root.get(Promocode_.discountValue), discountInterval.get(1));
            return cb.and(discountFromPredicate, discountToPredicate);
        };
    }

    public static Specification<Promocode> promocode(String promocodeText) {
        return (Root<Promocode> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.like(root.get(Promocode_.promocodeCode), "%" + promocodeText + "%");
        };
    }

    public static Specification<Promocode> buildSpecification(String code, List<LocalDate> startDate, List<LocalDate> endDate, List<BigDecimal> discount) {
        Specification<Promocode> spec = Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if (code != null) {
            spec = spec.and(PromocodeSpecification.promocode(code));
        }
        if (startDate != null) {
            spec = spec.and(PromocodeSpecification.startDate(startDate));
        }
        if(endDate != null) {
            spec = spec.and(PromocodeSpecification.endDate(endDate));
        }
        if(discount != null) {
            spec = spec.and(PromocodeSpecification.discount(discount));
        }
        return spec;
    }
}