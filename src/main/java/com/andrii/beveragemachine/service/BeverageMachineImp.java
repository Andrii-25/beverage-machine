package com.andrii.beveragemachine.service;

import com.andrii.beveragemachine.entity.Stats;
import com.andrii.beveragemachine.entity.Coin;
import com.andrii.beveragemachine.entity.Money;
import com.andrii.beveragemachine.utils.Bucket;
import com.andrii.beveragemachine.utils.Inventory;
import com.andrii.beveragemachine.entity.Banknote;
import com.andrii.beveragemachine.entity.Product;
import com.andrii.beveragemachine.exception.NotFullPaidException;
import com.andrii.beveragemachine.exception.SoldOutException;
import com.andrii.beveragemachine.exception.NoRemainderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class BeverageMachineImp implements BeverageMachine {

    private final Logger log = LoggerFactory.getLogger(BeverageMachineImp.class);

    private final Inventory<Banknote> banknoteInventory = new Inventory<Banknote>();
    private final Inventory<Product> productInventory = new Inventory<Product>();
    private final Inventory<Coin> coinInventory = new Inventory<Coin>();
    private double totalSales;
    private Product currentProduct;
    private double currentBalance;

    public void initialize() {
        for (Banknote b : Banknote.values()) {
            banknoteInventory.put(b, 30);
        }
        for (Coin c : Coin.values()) {
            coinInventory.put(c, 30);
        }
        for (Product p : Product.values()) {
            productInventory.put(p, 30);
        }
        log.info("Initialized machine!");
    }

    public void selectProduct(Product product) throws SoldOutException {
            if (productInventory.hasItem(product)) {
                currentProduct = product;
                log.info("Selected product: " + product.getName());
                return;
            }
            throw new SoldOutException("Sold out, please buy another item!");
    }

    public void insertCash(Banknote banknote, Coin coin) {
        try {
            if(banknote != null || coin != null) {
                assert banknote != null;
                currentBalance = currentBalance + banknote.getDenomination();
                banknoteInventory.add(banknote);
                if (coin != null) {
                    currentBalance = currentBalance + ((float) coin.getDenomination() / 100);
                    coinInventory.add(coin);
                }
            }
            log.info("Current balance: " + currentBalance);
        } catch(Exception e) {
            log.error(e.getMessage());
        }
    }

    public Bucket<Product, List<Money>> collectProductAndRemainder() {
        Product product = collectProduct();
        totalSales = totalSales + currentProduct.getPrice();
        List<Money> remainder = collectRemainder();
        return new Bucket<Product, List<Money>>(product, remainder);
    }

    private Product collectProduct() throws NoRemainderException, NotFullPaidException {
        if (isFullPaid()) {
            if (hasRemainder()) {
                productInventory.deduct(currentProduct);
                return currentProduct;
            }
            throw new NoRemainderException("Not sufficient change in inventory!");
        }
        double remainingBalance = currentProduct.getPrice() - currentBalance;
        throw new NotFullPaidException("Price not fully paid, remaining: ", remainingBalance);
    }

    private List<Money> collectRemainder() {
        double remainderAmount = currentBalance - currentProduct.getPrice();
        List<Money> remainder = getRemainder(remainderAmount);
        updateBanknoteInventory(remainder);
        updateCoinInventory(remainder);
        currentBalance = 0;
        currentProduct = null;
        return remainder;
    }

    public List<Money> refund() {
        List<Money> refund = getRemainder(currentBalance);
        updateBanknoteInventory(refund);
        updateCoinInventory(refund);
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

    private List<Money> getRemainder(double amount) {
        List<Banknote> banknotes = new ArrayList<Banknote>();
        List<Coin> coins = new ArrayList<Coin>();
        List<Money> money = new ArrayList<>();

        if (amount > 0) {
            double balance = amount;
            while (balance > 0) {
                if(balance >= ((float)Coin.TWENTY_FIVE.getDenomination() / 100) && coinInventory.hasItem(Coin.TWENTY_FIVE))  {
                    coins.add(Coin.TWENTY_FIVE);
                    balance = balance - ((float)Coin.TWENTY_FIVE.getDenomination() / 100);
                    continue;
                }
                else if(balance >= ((float)Coin.FIFTY.getDenomination() / 100) && coinInventory.hasItem(Coin.FIFTY)) {
                    coins.add(Coin.FIFTY);
                    balance = balance - ((float)Coin.FIFTY.getDenomination() / 100);
                    continue;
                    }
                else if (balance >= Banknote.ONE.getDenomination() && banknoteInventory.hasItem(Banknote.ONE)) {
                    banknotes.add(Banknote.ONE);
                    balance = balance - Banknote.ONE.getDenomination();
                    continue;

                } else if (balance >= Banknote.TWO.getDenomination() && banknoteInventory.hasItem(Banknote.TWO)) {
                    banknotes.add(Banknote.TWO);
                    balance = balance - Banknote.TWO.getDenomination();
                    continue;
                } else if (balance >= Banknote.FIVE.getDenomination() && banknoteInventory.hasItem(Banknote.FIVE)) {
                    banknotes.add(Banknote.FIVE);
                    balance = balance - Banknote.FIVE.getDenomination();
                    continue;
                } else if (balance >= Banknote.TEN.getDenomination() && banknoteInventory.hasItem(Banknote.TEN)) {
                    banknotes.add(Banknote.TEN);
                    balance = balance - Banknote.TEN.getDenomination();
                    continue;
                } else if (balance >= Banknote.TWENTY.getDenomination() && banknoteInventory.hasItem(Banknote.TWENTY)) {
                    banknotes.add(Banknote.TWENTY);
                    balance = balance - Banknote.TWENTY.getDenomination();
                    continue;
                } else {
                    throw new NoRemainderException("Not enough remainder, please try another product!");
                }
            }
        }
        money.add(new Money(banknotes, coins));
        return money;
    }

    public void reset() {
        banknoteInventory.clear();
        coinInventory.clear();
        productInventory.clear();
        totalSales = 0;
        currentProduct = null;
        currentBalance = 0;
        log.info("Reset machine!");
    }

    public Stats getStats() {
        return new Stats(totalSales, productInventory, banknoteInventory, coinInventory);
    }


    private boolean hasRemainder() {
        return hasRemainderForAmount(currentBalance - currentProduct.getPrice());
    }

    private boolean hasRemainderForAmount(double amount) {
        boolean hasRemainder = true;
        try {
            getRemainder(amount);
        } catch (NoRemainderException nre) {
            hasRemainder = false;
            log.error(nre.getMessage());
        }
        return hasRemainder;
    }

    private void updateBanknoteInventory(List<Money> remainder) {
        for (Money m : remainder) {
            for (Banknote b : m.getBanknotes()) {
                banknoteInventory.deduct(b);
            }
        }
    }

    private void updateCoinInventory(List<Money> remainder) {
        for (Money m : remainder) {
            for (Coin c : m.getCoins()) {
                coinInventory.deduct(c);
            }
        }
    }
}
