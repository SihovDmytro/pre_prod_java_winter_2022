package com.webShop.service;

import com.webShop.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CartService {
    void add(Product product);

    void changeCount(Product product, int count);

    void remove(Product product);

    boolean contains(Product product);

    int count();

    BigDecimal calculateTotal();

    Map<Product, Integer> getCart();

    void clear();
}
