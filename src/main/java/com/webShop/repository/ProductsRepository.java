package com.webShop.repository;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface ProductsRepository {
    List<Product> getProducts(ProductsPageBean bean, Connection connection);

    int countProducts(ProductsPageBean bean, Connection connection);

    Optional<Product> getProduct(int id, Connection connection);
}
