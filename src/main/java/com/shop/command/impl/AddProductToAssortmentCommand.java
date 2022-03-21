package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.Product;
import com.shop.service.AssortmentService;
import com.shop.strategy.Filler;
import com.shop.template.CannedFoodFillerTemplate;
import com.shop.template.FoodFillerTemplate;
import com.shop.template.FurnitureFillerTemplate;
import com.shop.template.ProductFillerTemplate;
import com.shop.util.Constants;
import com.shop.util.MenuUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddProductToAssortmentCommand extends Command {
    private Scanner scanner;
    private AssortmentService assortmentService;
    private Filler filler;
    private static final Logger LOG = LogManager.getLogger(AddProductToAssortmentCommand.class);

    public AddProductToAssortmentCommand(Scanner scanner, AssortmentService assortmentService, Filler filler) {
        this.scanner = scanner;
        this.assortmentService = assortmentService;
        this.filler = filler;
    }

    @Override
    public void execute() {
        LOG.trace("AddProductToAssortmentCommand start");
        MenuUtil.printProductListMenu();
        Map<String, ProductFillerTemplate> products = createProductContainer();
        String option = scanner.nextLine();
        LOG.debug("option: " + option);
        ProductFillerTemplate productFillerTemplate = products.get(option);
        if (productFillerTemplate != null) {
            boolean fillingRepeat = true;
            Product product = null;
            while (fillingRepeat) {
                try {
                    product = productFillerTemplate.fillProduct();
                    fillingRepeat = false;
                } catch (IllegalArgumentException exception) {
                    System.out.println(Constants.INVALID_INPUT);
                    LOG.error(Constants.INVALID_INPUT, exception);
                }
            }
            LOG.trace("product: " + product);
            assortmentService.addProduct(product);
        } else {
            System.out.println(Constants.UNKNOWN_PRODUCT);
            LOG.info(Constants.UNKNOWN_PRODUCT);
        }
        LOG.trace("AddProductToAssortmentCommand end");
    }

    private Map<String, ProductFillerTemplate> createProductContainer() {
        Map<String, ProductFillerTemplate> map = new HashMap<>();
        map.put("1", new ProductFillerTemplate(filler) {
            @Override
            protected void setOtherFields() {
            }
        });
        map.put("2", new FurnitureFillerTemplate(filler));
        map.put("3", new FoodFillerTemplate(filler));
        map.put("4", new CannedFoodFillerTemplate(filler));
        return map;
    }
}
