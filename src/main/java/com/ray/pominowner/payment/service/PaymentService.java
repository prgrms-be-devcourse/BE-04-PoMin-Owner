package com.ray.pominowner.payment.service;

import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.domain.PaymentStatus;
import com.ray.pominowner.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ray.pominowner.global.util.ExceptionMessage.NO_PAYMENT;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public Payment create(Payment payment) {
        return paymentRepository.save(payment);
    }


    public Payment canceled(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException(NO_PAYMENT.getMessage()));

        Payment canceled = Payment.builder()
                .id(paymentId)
                .amount(payment.getAmount())
                .provider(payment.getProvider())
                .status(PaymentStatus.CANCELLED)
                .build();

        return paymentRepository.save(canceled);
    }

}
