package com.webShop.service.impl;

import com.webShop.dao.ProducersDAO;
import com.webShop.entity.Producer;
import com.webShop.service.ProducersService;
import com.webShop.transaction.TransactionManager;

import java.util.List;

public class ProducersServiceImpl implements ProducersService {
    private TransactionManager transactionManager;
    private ProducersDAO producersDAO;

    public ProducersServiceImpl(TransactionManager transactionManager, ProducersDAO producersDAO) {
        this.transactionManager = transactionManager;
        this.producersDAO = producersDAO;
    }

    @Override
    public List<Producer> getAllProducers() {
        return transactionManager.doInTransaction((connection -> producersDAO.getAllProducers(connection)));
    }
}
