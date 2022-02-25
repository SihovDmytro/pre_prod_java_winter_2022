package com.task4;

import com.sun.org.apache.xpath.internal.operations.Or;
import com.task1.subtask1.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.*;

public class OrderManager {
    private TreeMap<Calendar, HashMap<Product, Integer>> orders = new TreeMap<>();
    private static final Logger LOG = LogManager.getLogger(OrderManager.class);

    public OrderManager() {
    }

    public BigDecimal makeOrder(Cart cart, Calendar orderDate) {
        BigDecimal totalPrice = cart.getTotalPrice();
        LOG.debug("Make order. Total price: " + totalPrice + " " + ShopProperties.getProperty("product.currency"));
        orders.put(orderDate, cart.getCartHashMap());
        cart.clearCart();
        return totalPrice;
    }

    private TreeMap<Calendar, HashMap<Product, Integer>> getOrders(Calendar start, Calendar end) {
        TreeMap<Calendar, HashMap<Product, Integer>> ordersForPeriod = new TreeMap<>();
        for (Map.Entry<Calendar, HashMap<Product, Integer>> entry : orders.entrySet()) {
            Calendar orderDate = entry.getKey();
            if (orderDate.after(start) && orderDate.before(end)) {
                ordersForPeriod.put(orderDate, entry.getValue());
            }
        }
        return ordersForPeriod;
    }
}
