package com.shop.util;

import com.shop.entity.Cart;
import com.shop.entity.Product;
import com.shop.service.OrderService;

import java.util.Calendar;
import java.util.Map;

public class OrderUtil {
    public static boolean isOrderExist(OrderService orderService) {
        if (orderService.getOrders().size() < 1) {
            System.out.println("You have no orders");
            return false;
        }
        return true;
    }

    public static void printOrder(Map.Entry<Calendar, Cart> order) {
        for (Map.Entry<Product, Integer> productEntry : order.getValue().entrySet()) {
            System.out.println(productEntry.getKey() + "quantity: " + productEntry.getValue() + "\n");
        }
    }
}
