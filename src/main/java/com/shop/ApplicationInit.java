package com.shop;

import com.shop.command.Command;
import com.shop.command.impl.*;
import com.shop.dao.AssortmentDAO;
import com.shop.dao.CartDAO;
import com.shop.dao.CartHistoryDAO;
import com.shop.dao.OrderDAO;
import com.shop.dao.impl.AssortmentDAOImpl;
import com.shop.dao.impl.CartDAOImpl;
import com.shop.dao.impl.CartHistoryDAOImpl;
import com.shop.dao.impl.OrderDAOImpl;
import com.shop.entity.Cart;
import com.shop.entity.CartHistory;
import com.shop.service.AssortmentService;
import com.shop.service.CartHistoryService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.impl.AssortmentServiceImpl;
import com.shop.service.impl.CartHistoryServiceImpl;
import com.shop.service.impl.CartServiceImpl;
import com.shop.service.impl.OrderServiceImpl;
import com.shop.util.MenuUtil;
import com.shop.util.ShopProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class ApplicationInit {
    private CartDAO cartDAO;
    private CartService cartService;
    private AssortmentDAO assortmentDAO;
    private AssortmentService assortmentService;
    private CartHistoryDAO cartHistoryDAO;
    private CartHistoryService cartHistoryService;
    private OrderDAO orderDAO;
    private OrderService orderService;
    private Scanner scanner;
    private static final Logger LOG = LogManager.getLogger(ApplicationInit.class);


    public ApplicationInit(Scanner scanner) {
        this.scanner = scanner;
    }

    private Map<String, Command> commandsContainer;

    public boolean init() {
        boolean continueInit = ShopProperties.loadProperties();
        if (continueInit) {
            cartDAO = new CartDAOImpl(new Cart());
            cartService = new CartServiceImpl(cartDAO);
            cartHistoryDAO = new CartHistoryDAOImpl(new CartHistory());
            cartHistoryService = new CartHistoryServiceImpl(cartHistoryDAO);
            assortmentDAO = new AssortmentDAOImpl();
            ProductFiller filler = chooseProductInputMode();
            LOG.debug("Product input mode: " + filler);
            assortmentService = new AssortmentServiceImpl(assortmentDAO, filler);
            orderDAO = new OrderDAOImpl(new TreeMap<>());
            orderService = new OrderServiceImpl(orderDAO);
            createContainerCommands();
        }
        return continueInit;
    }

    private ProductFiller chooseProductInputMode() {
        ProductFiller filler = null;
        boolean continueWork = true;
        while (continueWork) {
            MenuUtil.printProductInputMenu();
            String option = scanner.nextLine();
            switch (option) {
                case "1": {
                    filler = new ConsoleFiller(scanner);
                    continueWork = false;
                    break;
                }
                case "2": {
                    filler = new RandomFiller();
                    continueWork = false;
                    break;
                }
                default: {
                    System.out.println("Unknown product input mode");
                }
            }
        }
        return filler;
    }


    private void createContainerCommands() {
        commandsContainer = new HashMap<>();
        commandsContainer.put("-1", new ExitCommand(assortmentService));
        commandsContainer.put("0", new PrintAllProductsCommand(assortmentService));
        commandsContainer.put("1", new AddToCartCommand(assortmentService, cartService, cartHistoryService, scanner));
        commandsContainer.put("2", new PrintCartCommand(cartService));
        commandsContainer.put("3", new MakeOrderCommand(cartService, orderService, scanner));
        commandsContainer.put("4", new PrintLastNItemsCommand(cartHistoryService));
        commandsContainer.put("5", new PrintOrdersForPeriodCommand(orderService, scanner));
        commandsContainer.put("6", new PrintOrderByDateCommand(orderService, scanner));
        commandsContainer.put("7", new AddProductToAssortmentCommand(scanner, assortmentService));
    }

    public Map<String, Command> getCommandsContainer() {
        return commandsContainer;
    }
}
