package com.shop;

import com.shop.command.Command;
import com.shop.command.impl.AddToCartCommand;
import com.shop.command.impl.ExitCommand;
import com.shop.command.impl.MakeOrderCommand;
import com.shop.command.impl.PrintCartCommand;
import com.shop.command.impl.PrintLast5ItemsCommand;
import com.shop.command.impl.PrintOrderByDateCommand;
import com.shop.command.impl.PrintOrdersForPeriodCommand;
import com.shop.dao.AssortmentDAO;
import com.shop.dao.CartDAO;
import com.shop.dao.CartHistoryDAO;
import com.shop.dao.OrderDAO;
import com.shop.dao.impl.AssortmentDAOImpl;
import com.shop.dao.impl.CartDAOImpl;
import com.shop.dao.impl.CartHistoryDAOImpl;
import com.shop.dao.impl.OrderDAOImpl;
import com.shop.service.AssortmentService;
import com.shop.service.CartHistoryService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.impl.AssortmentServiceImpl;
import com.shop.service.impl.CartHistoryServiceImpl;
import com.shop.service.impl.CartServiceImpl;
import com.shop.service.impl.OrderServiceImpl;
import com.shop.util.ShopProperties;

import java.util.HashMap;
import java.util.Map;

public class ApplicationInit {
    private CartDAO cartDAO;
    private CartService cartService;
    private AssortmentDAO assortmentDAO;
    private AssortmentService assortmentService;
    private CartHistoryDAO cartHistoryDAO;
    private CartHistoryService cartHistoryService;
    private OrderDAO orderDAO;
    private OrderService orderService;

    private Map<String, Command> commandsContainer;

    public boolean init() {
        boolean continueInit = ShopProperties.loadProperties();
        if (continueInit) {
            cartDAO = new CartDAOImpl();
            cartService = new CartServiceImpl(cartDAO);
            cartHistoryDAO = new CartHistoryDAOImpl();
            cartHistoryService = new CartHistoryServiceImpl(cartHistoryDAO);
            assortmentDAO = new AssortmentDAOImpl();
            assortmentService = new AssortmentServiceImpl(assortmentDAO);
            orderDAO = new OrderDAOImpl(cartService);
            orderService = new OrderServiceImpl(orderDAO);
            createContainerCommands();
        }
        return continueInit;
    }

    private void createContainerCommands() {
        commandsContainer = new HashMap<>();
        commandsContainer.put("-1", new ExitCommand());
        commandsContainer.put("1", new AddToCartCommand(assortmentService, cartService, cartHistoryService));
        commandsContainer.put("2", new PrintCartCommand(cartService));
        commandsContainer.put("3", new MakeOrderCommand(cartService, orderService));
        commandsContainer.put("4", new PrintLast5ItemsCommand(cartHistoryService));
        commandsContainer.put("5", new PrintOrdersForPeriodCommand(orderService));
        commandsContainer.put("6", new PrintOrderByDateCommand(orderService));
    }


    public Map<String, Command> getCommandsContainer() {
        return commandsContainer;
    }
}
