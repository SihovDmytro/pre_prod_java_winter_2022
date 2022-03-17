package com.shop.service.impl;

import com.shop.dao.OrderDAO;
import com.shop.entity.Cart;
import com.shop.service.OrderService;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void add(Cart products, Calendar orderDate) {
        orderDAO.add(products, orderDate);
    }

    @Override
    public TreeMap<Calendar, Cart> getOrders() {
        return orderDAO.getOrders();
    }

    @Override
    public TreeMap<Calendar, Cart> getOrdersForPeriod(Calendar start, Calendar end) {
        return orderDAO.getOrdersForPeriod(start, end);
    }

    @Override
    public Map.Entry<Calendar, Cart> getOrderByDate(Calendar date) {
        return orderDAO.getOrderByDate(date);
    }
}
