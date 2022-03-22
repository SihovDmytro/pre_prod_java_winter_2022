package com.shop.service;

import com.shop.entity.Product;

import java.util.List;

public interface AssortmentService {
    List<Product> getProductList();

    Product getProduct(int id);

    void addProduct(Product product);
}
