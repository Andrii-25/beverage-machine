package com.andrii.beveragemachine.utils;

public class Bucket<Product, Remainder> {
    private final Product product;
    private final Remainder remainder;

    public Bucket(Product product, Remainder remainder) {
        this.product = product;
        this.remainder = remainder;
    }

    public Product getProduct() {
        return product;
    }

    public Remainder getRemainder() {
        return remainder;
    }
}
