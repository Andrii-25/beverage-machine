package com.andrii.beveragemachine.entity;

public enum Coin {

    TWENTY_FIVE(25),
    FIFTY(50);

    private final int denomination;

    private Coin(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }
}
