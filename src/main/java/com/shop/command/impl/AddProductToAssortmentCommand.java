package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.CannedFood;
import com.shop.entity.Food;
import com.shop.entity.Furniture;
import com.shop.entity.Product;
import com.shop.service.AssortmentService;
import com.shop.util.MenuUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class AddProductToAssortmentCommand extends Command {
    private Scanner scanner;
    private AssortmentService assortmentService;
    private static final Logger LOG = LogManager.getLogger(AddProductToAssortmentCommand.class);

    public AddProductToAssortmentCommand(Scanner scanner, AssortmentService assortmentService) {
        this.scanner = scanner;
        this.assortmentService = assortmentService;
    }

    @Override
    public void execute() {
        LOG.trace("AddProductToAssortmentCommand start");
        MenuUtil.printProductListMenu();
        String option = scanner.nextLine();
        LOG.debug("option: " + option);
        switch (option) {
            case "1": {
                assortmentService.addProduct(new Product());
                break;
            }
            case "2": {
                assortmentService.addProduct(new Furniture());
                break;
            }
            case "3": {
                assortmentService.addProduct(new Food());
                break;
            }
            case "4": {
                assortmentService.addProduct(new CannedFood());
                break;
            }
            default: {
                System.out.println("Unknown product");
                LOG.trace("Unknown product");
            }
        }
        LOG.trace("AddProductToAssortmentCommand end");
    }
}
