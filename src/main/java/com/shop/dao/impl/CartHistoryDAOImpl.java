package com.shop.dao.impl;

import com.shop.dao.CartHistoryDAO;
import com.shop.entity.CartHistory;
import com.shop.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CartHistoryDAOImpl implements CartHistoryDAO {
    private CartHistory cartHistory;
    private static final Logger LOG = LogManager.getLogger(CartHistoryDAOImpl.class);

    public CartHistoryDAOImpl(CartHistory cartHistory) {
        this.cartHistory = cartHistory;
    }

    @Override
    public CartHistory getCartHistory() {
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
