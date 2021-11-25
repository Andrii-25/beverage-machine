package com.andrii.beveragemachine.entity;

import java.util.List;

public class Money {

    private List<Banknote> banknotes;
    private List<Coin> coins;

    public Money() {
    }

    public Money(List<Banknote> banknotes, List<Coin> coins) {
        this.banknotes = banknotes;
        this.coins = coins;
    }

    public List<Banknote> getBanknotes() {
        return banknotes;
    }

    public void setBanknotes(List<Banknote> banknotes) {
        this.banknotes = banknotes;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
