package com.andrii.beveragemachine.service;

import com.andrii.beveragemachine.dto.Stats;
import com.andrii.beveragemachine.utils.Bucket;
import com.andrii.beveragemachine.repo.Inventory;
import com.andrii.beveragemachine.entity.Banknote;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.exception.NotFullPaidException;
import com.andrii.beveragemachine.exception.NotSufficientChangeException;
import com.andrii.beveragemachine.exception.SoldOutException;

import java.util.*;

public class BeverageMachineImp implements BeverageMachine {

    private final Inventory<Banknote> cashInventory = new Inventory<Banknote>();
    private final Inventory<Product> productInventory = new Inventory<Product>();
    private double totalSales;
    private Product currentProduct;
    private double currentBalance;

    public void initialize() {
        for (Banknote c : Banknote.values()) {
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

    public void insertCash(Banknote banknote) {
        currentBalance = currentBalance + banknote.getDenomination();
        cashInventory.add(banknote);
    }

    public Bucket<Product, List<Banknote>> collectProductAndChange() {
        Product product = collectProduct();
        totalSales = totalSales + currentProduct.getPrice();
        List<Banknote> change = collectChange();
        return new Bucket<Product, List<Banknote>>(product, change);
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

    private List<Banknote> collectChange() {
        double changeAmount = currentBalance - currentProduct.getPrice();
        List<Banknote> change = getChange(changeAmount);
        updateCashInventory(change);
        currentBalance = 0;
        currentProduct = null;
        return change;
    }

    public List<Banknote> refund() {
        List<Banknote> refund = getChange(currentBalance);
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

    private List<Banknote> getChange(double amount) {
        List<Banknote> changes = new ArrayList<Banknote>();

        if (amount > 0) {
            double balance = amount;
            while (balance > 0) {
                if(balance >= Banknote.QUARTER.getDenomination() && cashInventory.hasItem(Banknote.QUARTER))  {
                    changes.add(Banknote.QUARTER);
                    balance = balance - Banknote.QUARTER.getDenomination();
                    continue;
                }
                else if(balance >= Banknote.HALF.getDenomination() && cashInventory.hasItem(Banknote.HALF)) {
                        changes.add(Banknote.HALF);
                        balance = balance - Banknote.HALF.getDenomination();
                        continue;
                    }
                if (balance >= Banknote.ONE.getDenomination() && cashInventory.hasItem(Banknote.ONE)) {
                    changes.add(Banknote.ONE);
                    balance = balance - Banknote.ONE.getDenomination();
                    continue;

                } else if (balance >= Banknote.TWO.getDenomination() && cashInventory.hasItem(Banknote.TWO)) {
                    changes.add(Banknote.TWO);
                    balance = balance - Banknote.TWO.getDenomination();
                    continue;
                } else if (balance >= Banknote.FIVE.getDenomination() && cashInventory.hasItem(Banknote.FIVE)) {
                    changes.add(Banknote.FIVE);
                    balance = balance - Banknote.FIVE.getDenomination();
                    continue;
                } else if (balance >= Banknote.TEN.getDenomination() && cashInventory.hasItem(Banknote.TEN)) {
                    changes.add(Banknote.TEN);
                    balance = balance - Banknote.TEN.getDenomination();
                    continue;
                } else if (balance >= Banknote.TWENTY.getDenomination() && cashInventory.hasItem(Banknote.TWENTY)) {
                    changes.add(Banknote.TWENTY);
                    balance = balance - Banknote.TWENTY.getDenomination();
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

    private void updateCashInventory(List<Banknote> change) {
        for (Banknote c : change) {
            cashInventory.deduct(c);
        }
    }
}
