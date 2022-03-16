package com.shop.service.impl;

import com.shop.dao.AssortmentDAO;
import com.shop.entity.Product;
import com.shop.service.AssortmentService;

import java.util.List;

public class AssortmentServiceImpl implements AssortmentService {
    private AssortmentDAO assortmentDAO;

    public AssortmentServiceImpl(AssortmentDAO assortmentDAO) {
        this.assortmentDAO = assortmentDAO;
    }

    @Override
    public List<Product> getProductList() {
        return assortmentDAO.getProductList();
    }

    @Override
    public Product getProduct(int id) {
        return assortmentDAO.getProduct(id);
    }
}
