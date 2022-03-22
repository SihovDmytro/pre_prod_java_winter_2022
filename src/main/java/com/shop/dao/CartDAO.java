package com.shop.dao;

import com.shop.entity.Cart;
import com.shop.entity.Product;

import java.math.BigDecimal;

public interface CartDAO {
    Cart getCart();

    boolean add(Product product, int number);

    void clearCart();

    BigDecimal getTotalPrice();
}
