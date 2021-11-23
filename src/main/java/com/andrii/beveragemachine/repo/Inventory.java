package com.andrii.beveragemachine.repo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory<T> {
    private final Map<T, Integer> inventory = new HashMap<T, Integer>();

    public Map<T, Integer> getInventory() {
        return inventory;
    }

    public int getQuantity(T item) {
        Integer value = inventory.get(item);
        return value == null? 0 : value ;
    }

    public void add(T item) {
        int count = inventory.get(item);
        inventory.put(item, count+1);
    }

    public void deduct(T item) {
        if (hasItem(item)) {
            int count = inventory.get(item);
            inventory.put(item, count - 1);
        }
    }

    public boolean hasItem(T item) {
        return getQuantity(item) > 0;
    }

    public void clear() {
        inventory.clear();
    }

    public void put(T item, int quantity) {
        inventory.put(item, quantity);
    }

    public List getAll() {
        return new ArrayList(inventory.keySet());
    }
    public boolean hasItem(T item, int quantity) {
        return getQuantity(item) >= quantity;
    }

}
