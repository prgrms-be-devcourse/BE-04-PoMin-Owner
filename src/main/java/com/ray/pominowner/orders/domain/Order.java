package com.ray.pominowner.orders.domain;

import com.ray.pominowner.global.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "ORDERS")
public class Order extends BaseTimeEntity {

    @Id
    @Column(name = "ORDER_ID")
    @GeneratedValue
    private Long id;

    private String orderNumber;

    private Integer receiptNumber;

    @Enumerated(value = STRING)
    private OrderStatus status;

    private String requestedDetails;

    private Integer totalPrice;

    private String customerPhoneNumber;

    // STORE 와 다대일 매핑
    private Long storeId;

    private LocalDateTime reservationTime;

}
