package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.CartHistoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class PrintLast5ItemsCommand extends Command {
    private static final Logger LOG = LogManager.getLogger(PrintLast5ItemsCommand.class);
    private CartHistoryService cartHistoryService;

    public PrintLast5ItemsCommand(CartHistoryService cartHistoryService) {
        this.cartHistoryService = cartHistoryService;
    }

    @Override
    public void execute() {
        LOG.trace("PrintLast5ItemsCommand start");
        System.out.println("Last five items: ");
        List<Product> products = new ArrayList<>(cartHistoryService.getLastProducts(5));
        for (Product product : products)
            System.out.println(product);
        LOG.trace("PrintLast5ItemsCommand end");
    }
}
