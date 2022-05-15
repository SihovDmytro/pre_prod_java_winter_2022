package com.webShop.service.impl;

import com.webShop.dao.OrdersDAO;
import com.webShop.entity.Order;
import com.webShop.service.OrdersService;
import com.webShop.transaction.TransactionManager;

public class OrdersServiceImpl implements OrdersService {
    private TransactionManager transactionManager;
    private OrdersDAO ordersDAO;

    public OrdersServiceImpl(TransactionManager transactionManager, OrdersDAO ordersDAO) {
        this.transactionManager = transactionManager;
        this.ordersDAO = ordersDAO;
    }

    @Override
    public void add(Order order) {
        transactionManager.doInTransaction((connection -> ordersDAO.add(order, connection)));
    }
}
