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

public class PrintOrderByDateCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(PrintOrderByDateCommand.class);
    private OrderService orderService;
    private Scanner scanner;

    public PrintOrderByDateCommand(OrderService orderService, Scanner scanner) {
        this.orderService = orderService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LOG.trace("PrintOrderByDateCommand start");
        if (!OrderUtil.isOrderExist(orderService)) {
            LOG.trace("PrintOrderByDateCommand end");
            return;
        }
        SimpleDateFormat format = new SimpleDateFormat(ShopProperties.getProperty("datetime.format"));
        System.out.println("Enter date(" + format.toPattern() + "): ");
        Calendar date = DateUtil.readDateFromConsole(scanner, format);
        if (date != null) {
            Map.Entry<Calendar, Cart> nearestOrder = orderService.getOrderByDate(date);
            String orderDate = DateUtil.calendarToStringDatetime(nearestOrder.getKey());
            System.out.println("Order date: " + orderDate +
                    "\nProducts list: \n");
            OrderUtil.printOrder(nearestOrder);
        }
        LOG.trace("PrintOrderByDateCommand end");
    }
}
