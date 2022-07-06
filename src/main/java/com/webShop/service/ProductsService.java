package com.webShop.service;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;

import java.util.List;

public interface ProductsService {
    List<Product> getProducts(ProductsPageBean bean);

    int countProducts(ProductsPageBean bean);
}
