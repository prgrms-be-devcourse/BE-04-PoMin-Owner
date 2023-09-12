package com.ray.pominowner.orders.repository;

import com.ray.pominowner.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
