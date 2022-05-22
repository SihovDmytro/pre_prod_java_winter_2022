package com.webShop.dao.impl;

import com.webShop.entity.Product;
import com.webShop.dao.CartDAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartDAOImpl implements CartDAO {
    private HashMap<Product, Integer> cart = new HashMap<>();

    @Override
    public void add(Product product) {
        cart.put(product, 1);
    }

    @Override
    public void remove(Product product) {
        if (contains(product)) {
            cart.remove(product);
        }
    }

    @Override
    public void changeCount(Product product, int count) {
        if (cart.containsKey(product)) {
            cart.replace(product, count);
        }
    }

    @Override
    public boolean contains(Product product) {
        return cart.containsKey(product);
    }

    @Override
    public int count() {
        return cart.size();
    }

    @Override
    public BigDecimal calculateTotal() {
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            BigDecimal price = entry.getKey().getPrice();
            BigDecimal number = new BigDecimal(entry.getValue());
            totalPrice = totalPrice.add(price.multiply(number));
        }
        return totalPrice;
    }

    @Override
    public Map<Product, Integer> getCart() {
        return new HashMap<>(cart);
    }

    @Override
    public void clear() {
        cart.clear();
    }
}
