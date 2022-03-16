package com.shop.service.impl;

import com.shop.entity.Product;
import com.shop.dao.CartDAO;
import com.shop.service.CartService;

import java.math.BigDecimal;
import java.util.HashMap;

public class CartServiceImpl implements CartService {
    private CartDAO cartDAO;

    public CartServiceImpl(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }


    @Override
    public HashMap<Product, Integer> getCart() {
        return cartDAO.getCart();
    }

    @Override
    public boolean add(Product product, int number) {
        return cartDAO.add(product, number);
    }

    @Override
    public void clearCart() {
        cartDAO.clearCart();
    }

    @Override
    public BigDecimal getTotalPrice() {
        return cartDAO.getTotalPrice();
    }

}
