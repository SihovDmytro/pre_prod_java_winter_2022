package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.AssortmentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class PrintAllProductsCommand extends Command {
    AssortmentService assortmentService;
    private static final Logger LOG = LogManager.getLogger(PrintAllProductsCommand.class);

    public PrintAllProductsCommand(AssortmentService assortmentService) {
        this.assortmentService = assortmentService;
    }

    @Override
    public void execute() {
        LOG.debug("PrintAllProductsCommand start");
        List<Product> products = assortmentService.getProductList();
        System.out.println("Available products: \n");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product ID: " + i + "\n" + products.get(i));
        }
        LOG.debug("PrintAllProductsCommand end");
    }
}
