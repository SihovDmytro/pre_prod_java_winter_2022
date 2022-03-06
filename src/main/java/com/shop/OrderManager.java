package com.shop;

import com.shop.dao.entity.Product;
import com.shop.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class OrderManager {
    private TreeMap<Calendar, HashMap<Product, Integer>> orders = new TreeMap<>();
    private static final Logger LOG = LogManager.getLogger(OrderManager.class);

    public OrderManager() {
    }

    public int numberOfOrders() {
        return orders.size();
    }

    public BigDecimal makeOrder(Cart cart, Calendar orderDate) {
        BigDecimal totalPrice = cart.getTotalPrice();
        LOG.debug("Make order. Total price: " + totalPrice);
        HashMap<Product, Integer> productsInCart = cart.getCartHashMap();
        LOG.trace("productsInCart: " + productsInCart.size());
        orders.put(orderDate, productsInCart);
        cart.clearCart();
        return totalPrice;
    }

    public TreeMap<Calendar, HashMap<Product, Integer>> getOrders(Calendar start, Calendar end) {
        TreeMap<Calendar, HashMap<Product, Integer>> ordersForPeriod = new TreeMap<>();
        for (Map.Entry<Calendar, HashMap<Product, Integer>> entry : orders.entrySet()) {
            Calendar orderDate = entry.getKey();
            if (orderDate.after(start) && orderDate.before(end)) {
                ordersForPeriod.put(orderDate, entry.getValue());
            }
        }
        return ordersForPeriod;
    }

    public Map.Entry<Calendar, HashMap<Product, Integer>> getOrder(Calendar date) {
        if (orders.isEmpty()) return null;
        Iterator<Map.Entry<Calendar, HashMap<Product, Integer>>> iterator = orders.entrySet().iterator();
        Map.Entry<Calendar, HashMap<Product, Integer>> nearestOrder = iterator.next();
        long minDifference = Math.abs(date.getTimeInMillis() - nearestOrder.getKey().getTimeInMillis());
        LOG.trace("orderDate: " + Util.calendarToStringDatetime(nearestOrder.getKey()) +
                "\nminDifference: " + minDifference);
        while (iterator.hasNext()) {
            Map.Entry<Calendar, HashMap<Product, Integer>> order = iterator.next();
            long diff = Math.abs(date.getTimeInMillis() - order.getKey().getTimeInMillis());
            if (diff < minDifference) {
                minDifference = diff;
                nearestOrder = order;
                LOG.trace("orderDate: " + Util.calendarToStringDatetime(nearestOrder.getKey()) +
                        "\nminDifference: " + minDifference);
            }
        }
        return nearestOrder;
    }

}
