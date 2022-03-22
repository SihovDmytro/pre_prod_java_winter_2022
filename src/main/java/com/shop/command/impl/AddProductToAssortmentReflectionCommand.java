package com.shop.command.impl;

import com.shop.command.Command;
import com.shop.entity.CannedFood;
import com.shop.entity.Food;
import com.shop.entity.Furniture;
import com.shop.entity.Product;
import com.shop.reflection.filler.ReflectionFiller;
import com.shop.service.AssortmentService;
import com.shop.strategy.Filler;
import com.shop.util.Constants;
import com.shop.util.Localization;
import com.shop.util.MenuUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AddProductToAssortmentReflectionCommand extends Command {
    private Scanner scanner;
    private AssortmentService assortmentService;
    private Filler filler;
    private static final Logger LOG = LogManager.getLogger(AddProductToAssortmentCommand.class);

    public AddProductToAssortmentReflectionCommand(Scanner scanner, AssortmentService assortmentService, Filler filler) {
        this.scanner = scanner;
        this.assortmentService = assortmentService;
        this.filler = filler;
    }

    @Override
    public void execute() {
        LOG.trace("AddProductToAssortmentReflectionCommand start");
        MenuUtil.printProductListMenu();
        Map<String, Product> products = createProductContainer();
        String option = scanner.nextLine();
        LOG.debug("option: " + option);
        Product product = products.get(option);
        ReflectionFiller reflectionFiller = new ReflectionFiller(filler);
        if (product != null) {
            boolean fillingRepeat = true;
            while (fillingRepeat) {
                try {
                    product = reflectionFiller.fillProduct(product);
                    fillingRepeat = false;
                } catch (IllegalArgumentException | NoSuchMethodException | InvocationTargetException | IllegalAccessException exception) {
                    System.out.println(Localization.getResource("message.cannotAddProduct"));
                    LOG.error(Constants.INVALID_INPUT, exception);
                }
            }
            LOG.trace("product: " + product);
            assortmentService.addProduct(product);
        } else {
            System.out.println(Localization.getResource("message.unknownProduct"));
            LOG.info(Constants.UNKNOWN_PRODUCT);
        }
        LOG.trace("AddProductToAssortmentReflectionCommand end");
    }

    private Map<String, Product> createProductContainer() {
        Map<String, Product> map = new HashMap<>();
        map.put("1", new Furniture());
        map.put("2", new Food());
        map.put("3", new CannedFood());
        return map;
    }
}
