package com.shop.dao.impl;

import com.shop.dao.CartHistoryDAO;
import com.shop.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;

public class CartHistoryDAOImpl implements CartHistoryDAO {
    private LinkedHashMap<Product, Integer> cartHistory;
    private static final Logger LOG = LogManager.getLogger(CartHistoryDAOImpl.class);

    public CartHistoryDAOImpl(LinkedHashMap<Product, Integer> cartHistory) {
        this.cartHistory = cartHistory;
    }

    @Override
    public Map<Product, Integer> getCartHistory() {
        return cartHistory;
    }

    @Override
    public boolean add(Product product, int number) {
        if (number < 1) return false;
        int oldValue = 0;
        if (cartHistory.containsKey(product)) {
            oldValue = cartHistory.remove(product);
        }
        cartHistory.put(product, number + oldValue);
        LOG.debug("Add product to the cart history: " + product.getName());
        return true;
    }
}
