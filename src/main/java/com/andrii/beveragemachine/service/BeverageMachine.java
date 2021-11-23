package com.andrii.beveragemachine.service;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.entity.Cash;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.utils.Bucket;

import java.util.List;

public interface BeverageMachine {

    void initialize();

    void selectProduct(Product product);

    void insertCash(Cash cash);

    Bucket<Product, List<Cash>> collectProductAndChange();

    List<Cash> refund();

    void reset();

    Stats getStats();


}
