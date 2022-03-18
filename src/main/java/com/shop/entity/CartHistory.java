package com.shop.entity;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CartHistory implements Map<Product, Integer> {
    private Map<Product, Integer> cartHistory = new LinkedHashMap<>();

    @Override
    public int size() {
        return cartHistory.size();
    }

    @Override
    public boolean isEmpty() {
        return cartHistory.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cartHistory.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cartHistory.containsValue(value);
    }

    @Override
    public Integer get(Object key) {
        return cartHistory.get(key);
    }

    @Override
    public Integer put(Product key, Integer value) {
        return cartHistory.put(key, value);
    }

    @Override
    public Integer remove(Object key) {
        return cartHistory.remove(key);
    }

    @Override
    public void putAll(Map<? extends Product, ? extends Integer> m) {
        cartHistory.putAll(m);
    }

    @Override
    public void clear() {
        cartHistory.clear();
    }

    @Override
    public Set<Product> keySet() {
        return cartHistory.keySet();
    }

    @Override
    public Collection<Integer> values() {
        return cartHistory.values();
    }

    @Override
    public Set<Entry<Product, Integer>> entrySet() {
        return cartHistory.entrySet();
    }
}
