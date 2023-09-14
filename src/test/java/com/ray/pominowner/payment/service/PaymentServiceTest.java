package com.ray.pominowner.payment.service;

import com.ray.pominowner.payment.domain.PGType;
import com.ray.pominowner.payment.domain.Payment;
import com.ray.pominowner.payment.domain.PaymentStatus;
import com.ray.pominowner.payment.repository.PaymentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentService paymentService;

    @Test
    @DisplayName("Payment 저장에 성공한다.")
    public void successCreatePayment() {
        // given
        Payment payment = new Payment(1L, 1000, PaymentStatus.COMPLETE, PGType.TOSS);
        given(paymentRepository.save(payment)).willReturn(payment);

        // when
        Payment createdPayment = paymentService.create(payment);

        // then
        assertThat(createdPayment).isEqualTo(payment);
    }

}
