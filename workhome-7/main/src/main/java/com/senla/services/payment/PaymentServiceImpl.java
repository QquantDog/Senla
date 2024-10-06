package com.senla.services.payment;

import com.senla.dao.payment.PaymentDao;
import com.senla.models.payment.Payment;
import com.senla.util.service.AbstractLongIdGenericService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentServiceImpl extends AbstractLongIdGenericService<Payment> implements PaymentService {

    @Autowired
    private PaymentDao paymentDao;

    @Autowired
    private ModelMapper modelMapper;

    @PostConstruct
    @Override
    public void init() {
        super.abstractDao = paymentDao;
    }

    @Override
    public List<Payment> getAllByCustomerId(Long customerId) {
        return paymentDao.getPaymentsByCustomerId(customerId);
    }
}
