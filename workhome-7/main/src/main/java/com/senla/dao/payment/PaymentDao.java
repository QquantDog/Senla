package com.senla.dao.payment;

import com.senla.models.payment.Payment;
import com.senla.util.dao.AbstractLongDao;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PaymentDao extends AbstractLongDao<Payment> implements IPaymentDao {
    @Override
    @PostConstruct
    protected void init() {
        super.clazz = Payment.class;
    }

    @Override
    public List<Payment> getPaymentsByCustomerId(Long customerId) {
        TypedQuery<Payment> q = em.createQuery("""
                        select p from Payment p
                        join p.ride r
                        where r.customer.id = :customerId""", Payment.class);
        q.setParameter("customerId", customerId);
        return q.getResultList();
    }
}