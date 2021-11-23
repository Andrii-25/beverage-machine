package com.andrii.beveragemachine.service;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.utils.Bucket;
import com.andrii.beveragemachine.repo.Inventory;
import com.andrii.beveragemachine.entity.Cash;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.exception.NotFullPaidException;
import com.andrii.beveragemachine.exception.NotSufficientChangeException;
import com.andrii.beveragemachine.exception.SoldOutException;

import java.util.*;

public class BeverageMachineImp implements BeverageMachine {

    private final Inventory<Cash> cashInventory = new Inventory<Cash>();
    private final Inventory<Product> productInventory = new Inventory<Product>();
    private double totalSales;
    private Product currentProduct;
    private double currentBalance;

    public void initialize() {
        for (Cash c : Cash.values()) {
            cashInventory.put(c, 30);
        }
        for (Product p : Product.values()) {
            productInventory.put(p, 30);
        }
    }

    public void selectProduct(Product product) throws SoldOutException {
            if (productInventory.hasItem(product)) {
                currentProduct = product;
                return;
            }
            throw new SoldOutException("Sold out, please buy another item!");
    }

    public void insertCash(Cash cash) {
        currentBalance = currentBalance + cash.getDenomination();
        cashInventory.add(cash);
    }

    public Bucket<Product, List<Cash>> collectProductAndChange() {
        Product product = collectProduct();
        totalSales = totalSales + currentProduct.getPrice();
        List<Cash> change = collectChange();
        return new Bucket<Product, List<Cash>>(product, change);
    }

    private Product collectProduct() throws NotSufficientChangeException, NotFullPaidException {
        if (isFullPaid()) {
            if (hasSufficientChange()) {
                productInventory.deduct(currentProduct);
                return currentProduct;
            }
            throw new NotSufficientChangeException("Not sufficient change in inventory!");
        }
        double remainingBalance = currentProduct.getPrice() - currentBalance;
        throw new NotFullPaidException("Price not fully paid, remaining: ", remainingBalance);
    }

    private List<Cash> collectChange() {
        double changeAmount = currentBalance - currentProduct.getPrice();
        List<Cash> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentProduct = null;
        return change;
    }

    public List<Cash> refund() {
        List<Cash> refund = getChange(currentBalance);
        updateCashInventory(refund);
        currentBalance = 0;
        currentProduct = null;
        return refund;
    }

    private boolean isFullPaid() {
        if (currentBalance >= currentProduct.getPrice()) {
            return true;
        }
        return false;
    }

    private List<Cash> getChange(double amount) {
        List<Cash> changes = new ArrayList<Cash>();

        if (amount > 0) {
            double balance = amount;
            while (balance > 0) {
                if(balance >= Cash.QUARTER.getDenomination() && cashInventory.hasItem(Cash.QUARTER))  {
                    changes.add(Cash.QUARTER);
                    balance = balance - Cash.QUARTER.getDenomination();
                    continue;
                }
                else if(balance >= Cash.HALF.getDenomination() && cashInventory.hasItem(Cash.HALF)) {
                        changes.add(Cash.HALF);
                        balance = balance - Cash.HALF.getDenomination();
                        continue;
                    }
                if (balance >= Cash.ONE.getDenomination() && cashInventory.hasItem(Cash.ONE)) {
                    changes.add(Cash.ONE);
                    balance = balance - Cash.ONE.getDenomination();
                    continue;

                } else if (balance >= Cash.TWO.getDenomination() && cashInventory.hasItem(Cash.TWO)) {
                    changes.add(Cash.TWO);
                    balance = balance - Cash.TWO.getDenomination();
                    continue;
                } else if (balance >= Cash.FIVE.getDenomination() && cashInventory.hasItem(Cash.FIVE)) {
                    changes.add(Cash.FIVE);
                    balance = balance - Cash.FIVE.getDenomination();
                    continue;
                } else if (balance >= Cash.TEN.getDenomination() && cashInventory.hasItem(Cash.TEN)) {
                    changes.add(Cash.TEN);
                    balance = balance - Cash.TEN.getDenomination();
                    continue;
                } else if (balance >= Cash.TWENTY.getDenomination() && cashInventory.hasItem(Cash.TWENTY)) {
                    changes.add(Cash.TWENTY);
                    balance = balance - Cash.TWENTY.getDenomination();
                    continue;
                } else {
                    throw new NotSufficientChangeException("Not sufficient change, please try another product!");
                }
            }
        }
        return changes;
    }

    public void reset() {
        cashInventory.clear();
        productInventory.clear();
        totalSales = 0;
        currentProduct = null;
        currentBalance = 0;
    }

    public Stats getStats() {
        return new Stats(totalSales, productInventory, cashInventory);
    }


    private boolean hasSufficientChange() {
        return hasSufficientChangeForAmount(currentBalance - currentProduct.getPrice());
    }

    private boolean hasSufficientChangeForAmount(double amount) {
        boolean hasChange = true;
        try {
            getChange(amount);
        } catch (NotSufficientChangeException nsce) {
            hasChange = false;
        }
        return hasChange;
    }

    private void updateCashInventory(List<Cash> change) {
        for (Cash c : change) {
            cashInventory.deduct(c);
        }
    }
}
