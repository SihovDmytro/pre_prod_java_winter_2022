package com.shop.service;

import com.shop.entity.CartHistory;
import com.shop.entity.Product;

import java.util.List;

public interface CartHistoryService {
    CartHistory getCartHistory();

    boolean add(Product product, int number);

    List<Product> getLastProducts(int number);
}
