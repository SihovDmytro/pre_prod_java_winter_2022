package com.shop.service;

import com.shop.entity.Cart;
import com.shop.entity.Product;

import java.math.BigDecimal;

public interface CartService {
    Cart getCart();

    boolean add(Product product, int number);

    void clearCart();

    BigDecimal getTotalPrice();
}
