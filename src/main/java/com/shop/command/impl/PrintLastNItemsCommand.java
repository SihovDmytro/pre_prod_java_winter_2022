package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.CartHistoryService;
import com.shop.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PrintLastNItemsCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(PrintLastNItemsCommand.class);
    private CartHistoryService cartHistoryService;

    public PrintLastNItemsCommand(CartHistoryService cartHistoryService) {
        this.cartHistoryService = cartHistoryService;
    }

    @Override
    public void execute() {
        LOG.trace("PrintLastNItemsCommand start");
        System.out.println("Last " + Constants.LAST_ITEMS_COUNT + " items: ");
        List<Product> products = new ArrayList<>(cartHistoryService.getLastProducts(Constants.LAST_ITEMS_COUNT));
        for (Product product : products)
            System.out.println(product);
        LOG.trace("PrintLastNItemsCommand end");
    }
}
