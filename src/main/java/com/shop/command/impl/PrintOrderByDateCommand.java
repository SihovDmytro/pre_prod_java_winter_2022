package com.shop.command.impl;

import com.shop.Runner;
import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.OrderService;
import com.shop.util.DateUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PrintOrderByDateCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(PrintOrderByDateCommand.class);
    private OrderService orderService;

    public PrintOrderByDateCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void execute() {
        LOG.trace("PrintOrderByDateCommand start");
        if (orderService.getOrders().size() < 1) {
            System.out.println("You have no orders");
            LOG.trace("PrintOrderByDateCommand end");
            return;
        }
        System.out.println("Enter date(" + ShopProperties.getProperty("datetime.format") + "): ");
        String dateString = Runner.getScanner().nextLine();
        LOG.debug("dateString: " + dateString);
        Calendar date = DateUtil.stringToCalendar(dateString, new SimpleDateFormat(ShopProperties.getProperty("datetime.format")));
        if (date != null) {
            Map.Entry<Calendar, HashMap<Product, Integer>> nearestOrder = orderService.getOrderByDate(date);
            String orderDate = DateUtil.calendarToStringDatetime(nearestOrder.getKey());
            System.out.println("Order date: " + orderDate +
                    "\nProducts list: \n");
            for (Map.Entry<Product, Integer> productEntry : nearestOrder.getValue().entrySet()) {
                System.out.println(productEntry.getKey() + "quantity: " + productEntry.getValue() + "\n");
            }
        }
        LOG.trace("PrintOrderByDateCommand end");
    }
}
