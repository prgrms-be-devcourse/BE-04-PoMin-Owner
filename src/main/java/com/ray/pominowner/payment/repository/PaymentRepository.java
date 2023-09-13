package com.ray.pominowner.payment.repository;

import com.ray.pominowner.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
