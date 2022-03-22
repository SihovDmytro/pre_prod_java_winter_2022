package com.shop.dao.impl;

import com.shop.dao.OrderDAO;
import com.shop.entity.Cart;
import com.shop.util.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class OrderDAOImpl implements OrderDAO {
    private TreeMap<Calendar, Cart> orders;
    private static final Logger LOG = LogManager.getLogger(OrderDAOImpl.class);

    public OrderDAOImpl(TreeMap<Calendar, Cart> orders) {
        this.orders = orders;
    }

    public int numberOfOrders() {
        return orders.size();
    }

    @Override
    public void add(Cart products, Calendar orderDate) {
        orders.put(orderDate, products);
    }

    @Override
    public TreeMap<Calendar, Cart> getOrders() {
        return orders;
    }

    @Override
    public TreeMap<Calendar, Cart> getOrdersForPeriod(Calendar start, Calendar end) {
        TreeMap<Calendar, Cart> ordersForPeriod = new TreeMap<>();
        for (Map.Entry<Calendar, Cart> entry : orders.entrySet()) {
            Calendar orderDate = entry.getKey();
            if (orderDate.after(start) && orderDate.before(end)) {
                ordersForPeriod.put(orderDate, entry.getValue());
            }
        }
        return ordersForPeriod;
    }

    @Override
    public Map.Entry<Calendar, Cart> getOrderByDate(Calendar date) {
        if (orders.isEmpty()) return null;
        Iterator<Map.Entry<Calendar, Cart>> iterator = orders.entrySet().iterator();
        Map.Entry<Calendar, Cart> nearestOrder = iterator.next();
        long minDifference = Math.abs(date.getTimeInMillis() - nearestOrder.getKey().getTimeInMillis());
        LOG.trace("orderDate: " + DateUtil.calendarToStringDatetime(nearestOrder.getKey()) +
                "\nminDifference: " + minDifference);
        while (iterator.hasNext()) {
            Map.Entry<Calendar, Cart> order = iterator.next();
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
