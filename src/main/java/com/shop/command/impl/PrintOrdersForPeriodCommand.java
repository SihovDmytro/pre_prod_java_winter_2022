package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Cart;
import com.shop.service.OrderService;
import com.shop.util.DateUtil;
import com.shop.util.OrderUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class PrintOrdersForPeriodCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(PrintOrdersForPeriodCommand.class);
    private OrderService orderService;
    private Scanner scanner;

    public PrintOrdersForPeriodCommand(OrderService orderService, Scanner scanner) {
        this.orderService = orderService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LOG.trace("PrintOrdersForPeriodCommand start");
        if (!OrderUtil.isOrderExist(orderService)) {
            LOG.trace("PrintOrderByDateCommand end");
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat(ShopProperties.getProperty("date.format"));
        System.out.println("Enter start date(" + format.toPattern() + "): ");
        Calendar startDate = DateUtil.readDateFromConsole(scanner, format);
        System.out.println("Enter end date(" + format.toPattern() + "): ");
        Calendar endDate = DateUtil.readDateFromConsole(scanner, format);
        if (startDate != null && endDate != null) {
            TreeMap<Calendar, Cart> orders = orderService.getOrdersForPeriod(startDate, endDate);
            if (!orders.isEmpty()) {
                printOrders(orders);
            } else {
                System.out.println("You have no orders for this period");
                LOG.debug("have no orders for period: " +
                        DateUtil.calendarToStringDate(startDate) + " - " +
                        DateUtil.calendarToStringDate(endDate));
            }
        } else {
            System.out.println("Invalid date format");
        }
        LOG.trace("PrintOrdersForPeriodCommand end");
    }

    private static void printOrders(TreeMap<Calendar, Cart> orders) {
        System.out.println("Your orders: ");
        for (Map.Entry<Calendar, Cart> orderEntry : orders.entrySet()) {
            String orderDate = DateUtil.calendarToStringDatetime(orderEntry.getKey());
            System.out.println("Order date: " + orderDate +
                    "\nProducts list: \n");
            OrderUtil.printOrder(orderEntry);
        }
    }
}
