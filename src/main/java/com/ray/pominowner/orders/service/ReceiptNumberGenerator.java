package com.ray.pominowner.orders.service;

import org.springframework.stereotype.Component;

@Component
public class ReceiptNumberGenerator {

    private Integer value = 0;

    public Integer incrementAndGet() {
        this.value = this.value % 999 + 1;

        return this.value;
    }

}
