package com.shop.service.impl;

import com.shop.dao.CartHistoryDAO;
import com.shop.entity.CartHistory;
import com.shop.entity.Product;
import com.shop.service.CartHistoryService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CartHistoryServiceImpl implements CartHistoryService {
    private CartHistoryDAO cartHistoryDAO;

    public CartHistoryServiceImpl(CartHistoryDAO cartHistoryDAO) {
        this.cartHistoryDAO = cartHistoryDAO;
    }

    @Override
    public CartHistory getCartHistory() {
        return cartHistoryDAO.getCartHistory();
    }

    @Override
    public boolean add(Product product, int number) {
        return cartHistoryDAO.add(product, number);
    }

    @Override
    public List<Product> getLastProducts(int number) {
        List<Product> allProducts = new ArrayList<>(cartHistoryDAO.getCartHistory().keySet());
        Collections.reverse(allProducts);
        Iterator<Product> iterator = allProducts.iterator();
        List<Product> lastProducts = new ArrayList<>();
        for (int i = 0; i < number && iterator.hasNext(); i++) {
            lastProducts.add(iterator.next());
        }
        return lastProducts;
    }
}
