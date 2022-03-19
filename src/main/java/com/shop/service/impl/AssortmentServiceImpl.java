package com.shop.service.impl;

import com.shop.ProductFiller;
import com.shop.dao.AssortmentDAO;
import com.shop.entity.Product;
import com.shop.service.AssortmentService;

import java.util.List;

public class AssortmentServiceImpl implements AssortmentService {
    private AssortmentDAO assortmentDAO;
    private ProductFiller productFiller;

    public AssortmentServiceImpl(AssortmentDAO assortmentDAO, ProductFiller productFiller) {
        this.assortmentDAO = assortmentDAO;
        this.productFiller = productFiller;
    }

    @Override
    public List<Product> getProductList() {
        return assortmentDAO.getProductList();
    }

    @Override
    public Product getProduct(int id) {
        return assortmentDAO.getProduct(id);
    }

    @Override
    public void addProduct(Product product) {
        assortmentDAO.addProduct(productFiller.fill(product));
    }
}
