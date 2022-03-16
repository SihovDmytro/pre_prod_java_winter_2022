package com.shop.command.impl;

import com.shop.Runner;
import com.shop.command.Command;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.util.DateUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MakeOrderCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class);
    private CartService cartService;
    private OrderService orderService;

    public MakeOrderCommand(CartService cartService, OrderService orderService) {
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @Override
    public void execute() {
        LOG.trace("MakeOrderCommand start");
        if (!cartService.getCart().isEmpty()) {
            System.out.println("Enter order date(" + ShopProperties.getProperty("datetime.format") + "):");
            String textDate = Runner.getScanner().nextLine();
            LOG.trace("textDate: " + textDate);
            Calendar orderDate = DateUtil.stringToCalendar(textDate, new SimpleDateFormat(ShopProperties.getProperty("datetime.format")));
            if (orderDate != null) {
                System.out.println("Total price: " + orderService.makeOrder(orderDate) + " " + ShopProperties.getProperty("product.currency"));
            } else System.out.println("Invalid date format");
        } else System.out.println("Your cart is empty");

        LOG.trace("MakeOrderCommand end");
    }
}
