package com.shop.entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Map<Product, Integer> {
    private Map<Product, Integer> cart = new HashMap<>();

    @Override
    public int size() {
        return cart.size();
    }

    @Override
    public boolean isEmpty() {
        return cart.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cart.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cart.containsValue(value);
    }

    @Override
    public Integer get(Object key) {
        return cart.get(key);
    }

    @Override
    public Integer put(Product key, Integer value) {
        return cart.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return cart.remove(key);
    }

    @Override
    public void putAll(Map<? extends Product, ? extends Integer> m) {
        cart.putAll(m);
    }

    @Override
    public void clear() {
        cart.clear();
    }

    @Override
    public Set<Product> keySet() {
        return cart.keySet();
    }

    @Override
    public Collection<Integer> values() {
        return cart.values();
    }

    @Override
    public Set<Entry<Product, Integer>> entrySet() {
        return cart.entrySet();
    }
}
