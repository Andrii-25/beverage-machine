package com.andrii.beveragemachine.entity;

public enum Product {
    COKE("Coke", 10),
    PEPSI("Pepsi", 12),
    SODA("Soda", 8),
    TEA("Tea", 4),
    COFFEE("Coffee", 6);

    private final String name;
    private final double price;

    private Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}
