package com.webShop.service.impl;

import com.webShop.dao.CategoriesDAO;
import com.webShop.entity.Category;
import com.webShop.service.CategoriesService;
import com.webShop.transaction.TransactionManager;


import java.util.List;

public class CategoriesServiceImpl implements CategoriesService {
    private TransactionManager transactionManager;
    private CategoriesDAO categoriesDAO;

    public CategoriesServiceImpl(TransactionManager transactionManager, CategoriesDAO categoriesDAO) {
        this.transactionManager = transactionManager;
        this.categoriesDAO = categoriesDAO;
    }

    @Override
    public List<Category> getAllCategories() {
        return transactionManager.doInTransaction((connection -> categoriesDAO.getAllCategories(connection)));
    }
}
