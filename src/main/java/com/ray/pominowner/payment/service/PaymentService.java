package com.ray.pominowner.payment.service;

import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Long create(Payment payment) {
        return paymentRepository.save(payment).getId();
    }

}
