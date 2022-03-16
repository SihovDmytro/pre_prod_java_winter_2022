package com.shop.service;

import com.shop.entity.Product;

import java.math.BigDecimal;
import java.util.HashMap;

public interface CartService {
    HashMap<Product,Integer> getCart();
    boolean add(Product product, int number);
    void clearCart();
    BigDecimal getTotalPrice();
}
