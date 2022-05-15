package com.webShop.service.impl;

import com.webShop.dao.CartDAO;
import com.webShop.dao.impl.CartDAOImpl;
import com.webShop.entity.Product;
import com.webShop.service.CartService;

import java.math.BigDecimal;
import java.util.Map;

public class CartServiceImpl implements CartService {
    private CartDAO cartDAO = new CartDAOImpl();

    @Override
    public void add(Product product, int number) {
        cartDAO.add(product, number);
    }

    @Override
    public void remove(Product product, int number) {
        cartDAO.remove(product, number);
    }

    @Override
    public int count() {
        return cartDAO.count();
    }

    @Override
    public BigDecimal calculateTotal() {
        return cartDAO.calculateTotal();
    }

    @Override
    public Map<Product, Integer> getCart() {
        return cartDAO.getCart();
    }

    @Override
    public void clear() {
        cartDAO.clear();
    }
}
