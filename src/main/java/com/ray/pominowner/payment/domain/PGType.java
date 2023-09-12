package com.ray.pominowner.payment.domain;

import java.util.function.Function;

public enum PGType {
    TOSS(value -> value * 0.03),
    NHN(value -> value * 0.029),
    NICE(value -> value * 0.035);

    private final Function<Integer, Double> expression;

    PGType(Function<Integer, Double> expression) {
        this.expression = expression;
    }

    public int calculate(int value) {
        double apply = expression.apply(value);
        return (int) apply;
    }
}
