package com.andrii.beveragemachine.entity;

public enum Banknote {

    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    TWENTY(20);

    private double denomination;

    private Banknote(double denomination) {
        this.denomination = denomination;
    }

    public double getDenomination() {
        return denomination;
    }
}
