package com.webShop.dao;

import com.webShop.entity.Product;

import java.math.BigDecimal;
import java.util.Map;

public interface CartDAO {
    void add(Product product);

    void remove(Product product);

    void changeCount(Product product, int count);

    boolean contains(Product product);

    int count();

    BigDecimal calculateTotal();

    Map<Product, Integer> getCart();

    void clear();
}
