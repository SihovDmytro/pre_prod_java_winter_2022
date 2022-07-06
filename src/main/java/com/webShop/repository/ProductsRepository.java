package com.webShop.repository;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;

import java.sql.Connection;
import java.util.List;

public interface ProductsRepository {
    List<Product> getProducts(ProductsPageBean bean, Connection connection);

    int countProducts(ProductsPageBean bean, Connection connection);
}
