package com.shop.dao;

import com.shop.entity.Product;

import java.util.List;

public interface AssortmentDAO {
    List<Product> getProductList();

    Product getProduct(int id);

    void addProduct(Product product);
}
