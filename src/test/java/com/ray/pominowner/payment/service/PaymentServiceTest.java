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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("Payment 취소에 성공한다.")
    public void successCancelPayment() {
        // given
        Payment saved = new Payment(1L, 1000, PaymentStatus.COMPLETE, PGType.TOSS);
        Payment canceled = new Payment(1L, 1000, PaymentStatus.CANCELLED, PGType.TOSS);

        given(paymentRepository.findById(saved.getId())).willReturn(Optional.of(saved));
        given(paymentRepository.save(canceled)).willReturn(canceled);

        // when
        Payment payment = paymentService.canceled(saved.getId());

        // then
        assertThat(canceled).isEqualTo(payment);
    }

    @Test
    @DisplayName("PaymentId가 존재하지 않는 경우 취소에 실패한다.")
    public void failCancelPayment() {
        // given
        Payment saved = new Payment(1L, 1000, PaymentStatus.COMPLETE, PGType.TOSS);

        given(paymentRepository.findById(saved.getId())).willThrow(IllegalArgumentException.class);

        // when, then
        assertThatThrownBy(() -> paymentService.canceled(saved.getId()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
