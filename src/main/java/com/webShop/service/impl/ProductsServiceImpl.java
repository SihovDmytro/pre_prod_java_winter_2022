package com.webShop.service.impl;

import com.webShop.entity.Product;
import com.webShop.entity.ProductsPageBean;
import com.webShop.repository.ProductsRepository;
import com.webShop.service.ProductsService;
import com.webShop.transaction.TransactionManager;

import java.util.List;
import java.util.Optional;

public class ProductsServiceImpl implements ProductsService {
    private ProductsRepository productsRepository;
    private TransactionManager transactionManager;

    public ProductsServiceImpl(TransactionManager transactionManager, ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Product> getProducts(ProductsPageBean bean) {
        return transactionManager.doInTransaction((connection -> productsRepository.getProducts(bean, connection)));
    }

    @Override
    public int countProducts(ProductsPageBean bean) {
        return transactionManager.doInTransaction((connection -> productsRepository.countProducts(bean, connection)));
    }

    @Override
    public Optional<Product> getProduct(int id) {
        return transactionManager.doInTransaction(connection -> productsRepository.getProduct(id, connection));
    }
}
