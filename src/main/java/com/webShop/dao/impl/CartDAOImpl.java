package com.webShop.dao.impl;

import com.webShop.entity.Product;
import com.webShop.dao.CartDAO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartDAOImpl implements CartDAO {
    private HashMap<Product, Integer> cart = new HashMap<>();

    @Override
    public void add(Product product, int number) {
        if (number < 1) return;
        if (!cart.containsKey(product)) {
            cart.put(product, number);
        } else {
            cart.replace(product, cart.get(product) + number);
        }
    }

    @Override
    public void remove(Product product, int number) {
        if (cart.containsKey(product)) {
            int oldCount = cart.get(product);
            if (oldCount > number) {
                cart.remove(product);
            } else {
                cart.replace(product, oldCount - number);
            }
        }
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
