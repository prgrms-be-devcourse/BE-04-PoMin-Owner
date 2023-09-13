package com.ray.pominowner.payment.service;

import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }

}
