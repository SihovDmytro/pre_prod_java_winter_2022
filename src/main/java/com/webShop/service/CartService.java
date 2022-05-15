package com.webShop.service;

import com.webShop.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {
    void add(Product product, int number);

    void remove(Product product, int number);

    int count();

    BigDecimal calculateTotal();

    Map<Product, Integer> getCart();

    void clear();
}
