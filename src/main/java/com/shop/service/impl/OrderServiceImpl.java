package com.shop.service.impl;

import com.shop.dao.OrderDAO;
import com.shop.entity.Product;
import com.shop.service.OrderService;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;

    public OrderServiceImpl(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void add(HashMap<Product, Integer> products, Calendar orderDate) {
        orderDAO.add(products, orderDate);
    }

    @Override
    public TreeMap<Calendar, HashMap<Product, Integer>> getOrders() {
        return orderDAO.getOrders();
    }

    @Override
    public TreeMap<Calendar, HashMap<Product, Integer>> getOrdersForPeriod(Calendar start, Calendar end) {
        return orderDAO.getOrdersForPeriod(start, end);
    }

    @Override
    public Map.Entry<Calendar, HashMap<Product, Integer>> getOrderByDate(Calendar date) {
        return orderDAO.getOrderByDate(date);
    }
}
