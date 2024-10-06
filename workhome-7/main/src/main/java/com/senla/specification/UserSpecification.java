package com.senla.specification;

import com.senla.models.user.User;
import com.senla.models.user.User_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class UserSpecification {
    public static Specification<User> hasEmail(String email) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(User_.email), email);
        };
    }
    public static Specification<User> hasFirstName(String firstName) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(User_.firstName), firstName);
        };
    }
    public static Specification<User> hasLastName(String lastName) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.equal(root.get(User_.lastName), lastName);
        };
    }

    public static Specification<User> hasFromRegistrationDate(LocalDateTime fromRegistrationDate) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.greaterThanOrEqualTo(root.get(User_.registrationDate), fromRegistrationDate);
        };
    }
    public static Specification<User> hasToRegistrationDate(LocalDateTime toRegistrationDate) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            return cb.lessThan(root.get(User_.registrationDate), toRegistrationDate);
        };
    }

    public static Specification<User> buildSpecification(String email, String firstName, String lastName, LocalDateTime from, LocalDateTime to) {
        Specification<User> spec = Specification.where(null);
        if(email != null) {
            spec = spec.and(UserSpecification.hasEmail(email));
        }
        if(firstName != null) {
            spec = spec.and(UserSpecification.hasFirstName(firstName));
        }
        if(lastName != null) {
            spec = spec.and(UserSpecification.hasLastName(lastName));
        }
        if(from != null) {
            spec = spec.and(UserSpecification.hasFromRegistrationDate(from));
        }
        if(to != null) {
            spec = spec.and(UserSpecification.hasToRegistrationDate(to));
        }
        return spec;
    }
//    UserFilter + сделать в 7 домашке
//

//    [ a,b )
//    public static Specification<User> registrationDateBetween(LocalDateTime from, LocalDateTime to) {
//        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
//            Predicate fromPredicate = cb.conjunction();
//            Predicate toPredicate =   cb.conjunction();
//            if(from != null) {
//                fromPredicate = cb.greaterThanOrEqualTo(root.get(User_.registrationDate), from);
//            }
//            if(to != null) {
//                toPredicate = cb.lessThan(root.get(User_.registrationDate), to);
//            }
//            return cb.and(fromPredicate, toPredicate);
//        };
//    }


}
