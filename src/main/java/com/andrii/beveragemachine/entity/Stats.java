package com.andrii.beveragemachine.entity;

import com.andrii.beveragemachine.utils.Inventory;

public class Stats {

    private double totalSales;
    private Inventory<Product> productInventory;
    private Inventory<Banknote> banknoteInventoryInventory;
    private Inventory<Coin> coinInventory;

    public Stats() {
    }

    public Stats(double totalSales, Inventory<Product> productInventory, Inventory<Banknote> banknoteInventoryInventory, Inventory<Coin> coinInventory) {
        this.totalSales = totalSales;
        this.productInventory = productInventory;
        this.banknoteInventoryInventory = banknoteInventoryInventory;
        this.coinInventory = coinInventory;
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

    public Inventory<Banknote> getBanknoteInventoryInventory() {
        return banknoteInventoryInventory;
    }

    public void setBanknoteInventoryInventory(Inventory<Banknote> banknoteInventoryInventory) {
        this.banknoteInventoryInventory = banknoteInventoryInventory;
    }

    public Inventory<Coin> getCoinInventory() {
        return coinInventory;
    }

    public void setCoinInventory(Inventory<Coin> coinInventory) {
        this.coinInventory = coinInventory;
    }
}
