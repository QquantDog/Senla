package com.senla.services.payment;

import com.senla.models.payment.Payment;
import com.senla.util.service.GenericService;

import java.util.List;

public interface PaymentService extends GenericService<Payment, Long> {
    List<Payment> getAllByCustomerId(Long customerId);
}
