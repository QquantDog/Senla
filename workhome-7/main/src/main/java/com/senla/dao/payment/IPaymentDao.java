package com.senla.dao.payment;

import com.senla.models.payment.Payment;
import com.senla.util.dao.GenericDao;

import java.util.List;


public interface IPaymentDao extends GenericDao<Payment, Long> {
    List<Payment> getPaymentsByCustomerId(Long customerId);
}
