package com.andrii.beveragemachine.service;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.entity.Banknote;
import com.andrii.beveragemachine.entity.Coin;
import com.andrii.beveragemachine.entity.Money;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.utils.Bucket;

import java.util.List;

public interface BeverageMachine {

    void initialize();

    void selectProduct(Product product);

    void insertCash(Banknote banknote, Coin coin);

    Bucket<Product, List<Money>> collectProductAndRemainder();

    List<Money> refund();

    void reset();

    Stats getStats();


}
