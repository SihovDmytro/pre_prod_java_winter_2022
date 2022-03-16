package com.shop.dao;

import com.shop.entity.Product;

import java.util.Map;

public interface CartHistoryDAO {
    Map<Product,Integer> getCartHistory();
    boolean add(Product product, int number);
}
