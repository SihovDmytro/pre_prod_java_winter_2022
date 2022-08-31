package com.webShop.service;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;

import java.util.List;
import java.util.Optional;

public interface ProductsService {
    List<Product> getProducts(ProductsPageBean bean);

    int countProducts(ProductsPageBean bean);

    Optional<Product> getProduct(int id);
}
