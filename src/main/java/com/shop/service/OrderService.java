package com.shop.service;

import com.shop.entity.Cart;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

public interface OrderService {
    void add(Cart products, Calendar orderDate);

    TreeMap<Calendar, Cart> getOrders();

    TreeMap<Calendar, Cart> getOrdersForPeriod(Calendar start, Calendar end);

    Map.Entry<Calendar, Cart> getOrderByDate(Calendar date);
}
