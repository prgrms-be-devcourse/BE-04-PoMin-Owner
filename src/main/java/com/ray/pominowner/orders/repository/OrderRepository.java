package com.ray.pominowner.orders.repository;

import com.ray.pominowner.orders.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByStoreId(Long storeId);

}
