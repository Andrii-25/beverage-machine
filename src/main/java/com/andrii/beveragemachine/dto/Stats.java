package com.andrii.beveragemachine.dto;

import com.andrii.beveragemachine.entity.Cash;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.repo.Inventory;

public class Stats {

    private double totalSales;
    private Inventory<Product> productInventory;
    private Inventory<Cash> cashInventory;

    public Stats() {
    }

    public Stats(double totalSales, Inventory<Product> productInventory, Inventory<Cash> cashInventory) {
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

    public Inventory<Cash> getCashInventory() {
        return cashInventory;
    }

    public void setCashInventory(Inventory<Cash> cashInventory) {
        this.cashInventory = cashInventory;
    }
}
