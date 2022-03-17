package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.AssortmentService;
import com.shop.service.CartHistoryService;
import com.shop.service.CartService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class AddToCartCommand extends Command {
    private AssortmentService assortmentService;
    private CartService cartService;
    private CartHistoryService cartHistoryService;
    private Scanner scanner;
    private static final Logger LOG = LogManager.getLogger(AddToCartCommand.class);

    public AddToCartCommand(AssortmentService assortmentService, CartService cartService, CartHistoryService cartHistoryService, Scanner scanner) {
        this.assortmentService = assortmentService;
        this.cartService = cartService;
        this.cartHistoryService = cartHistoryService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        LOG.trace("BuyProductCommand start");
        System.out.println("What product would you like to buy? Select id: ");
        try {
            int productID = Integer.parseInt(scanner.nextLine());
            LOG.debug("productID: " + productID);
            Product product = assortmentService.getProduct(productID);
            if (product == null) {
                System.out.println("This product is not available.");
            } else {
                System.out.println("Select quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                LOG.debug("quantity: " + quantity);
                cartService.add(product, quantity);
                cartHistoryService.add(product, quantity);
                System.out.println("Product '" + product.getName() + "' was added to the cart.");
            }
        } catch (NumberFormatException exception) {
            LOG.debug("Cannot read int value");
            System.out.println("This isn't number");
        }
        LOG.trace("BuyProductCommand end");
    }
}
