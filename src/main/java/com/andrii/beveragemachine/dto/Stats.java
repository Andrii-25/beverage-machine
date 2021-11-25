package com.andrii.beveragemachine.dto;

import com.andrii.beveragemachine.entity.Banknote;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.repo.Inventory;

public class Stats {

    private double totalSales;
    private Inventory<Product> productInventory;
    private Inventory<Banknote> cashInventory;

    public Stats() {
    }

    public Stats(double totalSales, Inventory<Product> productInventory, Inventory<Banknote> cashInventory) {
        this.totalSales = totalSales;
        this.productInventory = productInventory;
        this.cashInventory = cashInventory;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public Inventory<Product> getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Inventory<Product> productInventory) {
        this.productInventory = productInventory;
    }

    public Inventory<Banknote> getCashInventory() {
        return cashInventory;
    }

    public void setCashInventory(Inventory<Banknote> cashInventory) {
        this.cashInventory = cashInventory;
    }
}
