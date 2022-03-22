package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class PrintCartCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(PrintCartCommand.class);
    private CartService cartService;

    public PrintCartCommand(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    public void execute() {
        LOG.trace("PrintCartCommand start");
        Map<Product, Integer> cartMap = cartService.getCart();
        LOG.debug("Cart has products: " + cartMap.size());
        if (!cartMap.isEmpty()) {
            System.out.println("Your cart: ");
            for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
                System.out.println(entry.getKey() + "quantity: " + entry.getValue() + "\n");
            }
            System.out.println("Total items: " + cartMap.size() +
                    "\nTotal price: " + cartService.getTotalPrice());
        } else System.out.println("Your cart is empty");
        LOG.trace("PrintCartCommand end");
    }
}
