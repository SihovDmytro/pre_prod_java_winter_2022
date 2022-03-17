package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.OrderService;
import com.shop.util.DateUtil;
import com.shop.util.OrderUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
        if (!OrderUtil.exist(orderService)) {
            LOG.trace("PrintOrderByDateCommand end");
            return;
        }
        System.out.println("Enter start date(" + ShopProperties.getProperty("date.format") + "): ");
        String startDateString = scanner.nextLine();
        LOG.debug("Start date: " + startDateString);
        SimpleDateFormat calendarFormat = new SimpleDateFormat(ShopProperties.getProperty("date.format"));
        Calendar startDate = DateUtil.stringToCalendar(startDateString, calendarFormat);
        System.out.println("Enter end date(" + ShopProperties.getProperty("date.format") + "): ");
        String endDateString = scanner.nextLine();
        LOG.debug("End date: " + endDateString);
        Calendar endDate = DateUtil.stringToCalendar(endDateString, calendarFormat);
        if (startDate != null && endDate != null) {
            TreeMap<Calendar, HashMap<Product, Integer>> orders = orderService.getOrdersForPeriod(startDate, endDate);
            if (!orders.isEmpty()) {
                printOrders(orders);
            } else {
                System.out.println("You have no orders for this period");
                LOG.debug("have no orders for period: " + startDateString + " - " + endDateString);
            }
        } else {
            System.out.println("Invalid date format");
        }
        LOG.trace("PrintOrdersForPeriodCommand end");
    }

    private static void printOrders(TreeMap<Calendar, HashMap<Product, Integer>> orders) {
        System.out.println("Your orders: ");
        for (Map.Entry<Calendar, HashMap<Product, Integer>> orderEntry : orders.entrySet()) {
            String orderDate = DateUtil.calendarToStringDatetime(orderEntry.getKey());
            System.out.println("Order date: " + orderDate +
                    "\nProducts list: \n");
            for (Map.Entry<Product, Integer> productEntry : orderEntry.getValue().entrySet()) {
                System.out.println(productEntry.getKey() + "quantity: " + productEntry.getValue() + "\n");
            }
        }
    }
}
