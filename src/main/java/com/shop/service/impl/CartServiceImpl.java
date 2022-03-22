package com.shop.service.impl;

import com.shop.dao.CartDAO;
import com.shop.entity.Cart;
import com.shop.entity.Product;
import com.shop.service.CartService;

import java.math.BigDecimal;

public class CartServiceImpl implements CartService {
    private CartDAO cartDAO;

    public CartServiceImpl(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }


    @Override
    public Cart getCart() {
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
