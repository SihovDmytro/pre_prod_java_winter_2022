package com.shop.dao;

import com.shop.entity.CartHistory;
import com.shop.entity.Product;

public interface CartHistoryDAO {
    CartHistory getCartHistory();
    boolean add(Product product, int number);
}
