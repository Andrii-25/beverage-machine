package com.andrii.beveragemachine.service;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.entity.Banknote;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.utils.Bucket;

import java.util.List;

public interface BeverageMachine {

    void initialize();

    void selectProduct(Product product);

    void insertCash(Banknote banknote);

    Bucket<Product, List<Banknote>> collectProductAndChange();

    List<Banknote> refund();

    void reset();

    Stats getStats();


}
