package com.shop.dao.impl;

import com.shop.dao.OrderDAO;
import com.shop.entity.Product;
import com.shop.service.CartService;
import com.shop.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class OrderDAOImpl implements OrderDAO {
    private TreeMap<Calendar, HashMap<Product, Integer>> orders = new TreeMap<>();
    private static final Logger LOG = LogManager.getLogger(OrderDAOImpl.class);
    private CartService cartService;

    public OrderDAOImpl(CartService cartService) {
        this.cartService = cartService;
    }

    public int numberOfOrders() {
        return orders.size();
    }

    @Override
    public BigDecimal makeOrder(Calendar orderDate) {
        BigDecimal totalPrice = cartService.getTotalPrice();
        LOG.debug("Make order. Total price: " + totalPrice);
        HashMap<Product, Integer> productsInCart = cartService.getCart();
        LOG.trace("productsInCart: " + productsInCart.size());
        orders.put(orderDate, productsInCart);
        cartService.clearCart();
        return totalPrice;
    }

    @Override
    public TreeMap<Calendar, HashMap<Product, Integer>> getOrders() {
        return orders;
    }

    @Override
    public TreeMap<Calendar, HashMap<Product, Integer>> getOrdersForPeriod(Calendar start, Calendar end) {
        TreeMap<Calendar, HashMap<Product, Integer>> ordersForPeriod = new TreeMap<>();
        for (Map.Entry<Calendar, HashMap<Product, Integer>> entry : orders.entrySet()) {
            Calendar orderDate = entry.getKey();
            if (orderDate.after(start) && orderDate.before(end)) {
                ordersForPeriod.put(orderDate, entry.getValue());
            }
        }
        return ordersForPeriod;
    }

    @Override
    public Map.Entry<Calendar, HashMap<Product, Integer>> getOrderByDate(Calendar date) {
        if (orders.isEmpty()) return null;
        Iterator<Map.Entry<Calendar, HashMap<Product, Integer>>> iterator = orders.entrySet().iterator();
        Map.Entry<Calendar, HashMap<Product, Integer>> nearestOrder = iterator.next();
        long minDifference = Math.abs(date.getTimeInMillis() - nearestOrder.getKey().getTimeInMillis());
        LOG.trace("orderDate: " + DateUtil.calendarToStringDatetime(nearestOrder.getKey()) +
                "\nminDifference: " + minDifference);
        while (iterator.hasNext()) {
            Map.Entry<Calendar, HashMap<Product, Integer>> order = iterator.next();
            long diff = Math.abs(date.getTimeInMillis() - order.getKey().getTimeInMillis());
            if (diff < minDifference) {
                minDifference = diff;
                nearestOrder = order;
                LOG.trace("orderDate: " + DateUtil.calendarToStringDatetime(nearestOrder.getKey()) +
                        "\nminDifference: " + minDifference);
            }
        }
        return nearestOrder;
    }

}
