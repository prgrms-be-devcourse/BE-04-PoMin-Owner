package com.ray.pominowner.payment.domain;

public enum PGType {

    TOSS(0.03),
    NHN(0.028),
    NICE(0.035);

    private final double paymentFee;

    PGType(double paymentFee) {
        this.paymentFee = paymentFee;
    }

    public int calculate(int amount) {
        return (int) (amount * paymentFee);
    }

}
