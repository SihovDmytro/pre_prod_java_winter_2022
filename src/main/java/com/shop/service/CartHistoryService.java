package com.shop.service;

import com.shop.entity.Product;

import java.util.List;
import java.util.Map;

public interface CartHistoryService {
    Map<Product,Integer> getCartHistory();
    boolean add(Product product, int number);
    List<Product> getLastProducts(int number);
}
