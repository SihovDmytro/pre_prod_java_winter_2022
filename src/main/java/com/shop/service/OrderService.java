package com.shop.service;

import com.shop.entity.Product;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public interface OrderService {
    BigDecimal makeOrder(Calendar orderDate);

    TreeMap<Calendar, HashMap<Product, Integer>> getOrders();

    TreeMap<Calendar, HashMap<Product, Integer>> getOrdersForPeriod(Calendar start, Calendar end);

    Map.Entry<Calendar, HashMap<Product, Integer>> getOrderByDate(Calendar date);
}
