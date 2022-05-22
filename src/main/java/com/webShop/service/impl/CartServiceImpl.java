package com.webShop.service.impl;

import com.webShop.dao.CartDAO;
import com.webShop.dao.impl.CartDAOImpl;
import com.webShop.entity.Product;
import com.webShop.service.CartService;

import java.math.BigDecimal;
import java.util.Map;

public class CartServiceImpl implements CartService {
    private CartDAO cartDAO;

    public CartServiceImpl(CartDAO cartDAO) {
        this.cartDAO = cartDAO;
    }

    @Override
    public void add(Product product) {
        cartDAO.add(product);
    }

    @Override
    public void changeCount(Product product, int count) {
        cartDAO.changeCount(product, count);
    }

    @Override
    public void remove(Product product) {
        cartDAO.remove(product);
    }

    @Override
    public boolean contains(Product product) {
        return cartDAO.contains(product);
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
