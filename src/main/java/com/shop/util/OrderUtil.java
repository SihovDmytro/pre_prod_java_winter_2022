package com.shop.util;

import com.shop.service.OrderService;

public class OrderUtil {
    public static boolean exist(OrderService orderService)
    {
        if (orderService.getOrders().size() < 1) {
            System.out.println("You have no orders");
            return false;
        }
        return true;
    }
}
