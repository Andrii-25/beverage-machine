package com.andrii.beveragemachine.entity;

public enum Coin {

    TWENTYFIVE(25),
    FIFTY(50);

    private final int denomination;

    private Coin(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }
}
