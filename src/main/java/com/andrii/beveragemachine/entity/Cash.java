package com.andrii.beveragemachine.entity;

public enum Cash {
    QUARTER(0.25),
    HALF(0.5),
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20);

    private double denomination;

    private Cash(double denomination) {
        this.denomination = denomination;
    }

    public double getDenomination() {
        return denomination;
    }
}
