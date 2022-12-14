package com.shop.initialization;

import com.shop.command.Command;
import com.shop.command.impl.AddProductToAssortmentCommand;
import com.shop.command.impl.AddProductToAssortmentReflectionCommand;
import com.shop.command.impl.AddToCartCommand;
import com.shop.command.impl.ChangeLanguageCommand;
import com.shop.command.impl.ExitCommand;
import com.shop.command.impl.MakeOrderCommand;
import com.shop.command.impl.PrintAllProductsCommand;
import com.shop.command.impl.PrintCartCommand;
import com.shop.command.impl.PrintLastNItemsCommand;
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
import com.shop.entity.Cart;
import com.shop.entity.CartHistory;
import com.shop.server.Server;
import com.shop.server.command.ServerCommand;
import com.shop.server.command.impl.http.ReturnNumberOfProductsHTTPCommand;
import com.shop.server.command.impl.http.ReturnProductInfoHTTPCommand;
import com.shop.server.command.impl.tcp.ReturnNumberOfProductsCommand;
import com.shop.server.command.impl.tcp.ReturnProductInfoCommand;
import com.shop.server.factory.HTTPClientThreadFactory;
import com.shop.server.factory.TCPClientThreadFactory;
import com.shop.server.util.ServerConstants;
import com.shop.service.AssortmentService;
import com.shop.service.CartHistoryService;
import com.shop.service.CartService;
import com.shop.service.OrderService;
import com.shop.service.impl.AssortmentServiceImpl;
import com.shop.service.impl.CartHistoryServiceImpl;
import com.shop.service.impl.CartServiceImpl;
import com.shop.service.impl.OrderServiceImpl;
import com.shop.strategy.ConsoleFiller;
import com.shop.strategy.Filler;
import com.shop.strategy.RandomFiller;
import com.shop.util.Localization;
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
    private Thread tcpServer;
    private Thread httpServer;
    private Filler filler;

    private static final Logger LOG = LogManager.getLogger(ApplicationInit.class);


    public ApplicationInit(Scanner scanner) {
        this.scanner = scanner;
    }

    private Map<String, Command> commandsContainer;
    private Map<String, Filler> fillersContainer;
    private Map<String, ServerCommand> TCPServerCommandContainer;
    private Map<String, ServerCommand> HTTPServerCommandContainer;

    public boolean init() {
        boolean initStatus = ShopProperties.loadProperties();
        if (initStatus) {
            Localization.loadLocalization(null);
            cartDAO = new CartDAOImpl(new Cart());
            cartService = new CartServiceImpl(cartDAO);
            cartHistoryDAO = new CartHistoryDAOImpl(new CartHistory());
            cartHistoryService = new CartHistoryServiceImpl(cartHistoryDAO);
            assortmentDAO = new AssortmentDAOImpl();
            assortmentService = new AssortmentServiceImpl(assortmentDAO);
            orderDAO = new OrderDAOImpl(new TreeMap<>());
            orderService = new OrderServiceImpl(orderDAO);
            chooseProductInputMode();
            LOG.debug("Product input mode: " + filler);
            createContainerCommands();
            createTCPServerCommandContainer();
            createHTTPServerCommandContainer();
            tcpServer = new Server(ServerConstants.TCP_SERVER_PORT, TCPServerCommandContainer, new TCPClientThreadFactory());
            httpServer = new Server(ServerConstants.HTTP_SERVER_PORT, HTTPServerCommandContainer, new HTTPClientThreadFactory());
            tcpServer.start();
            httpServer.start();
        }
        return initStatus;
    }

    private void chooseProductInputMode() {
        boolean repeat = true;
        createFillersContainer();
        while (repeat) {
            MenuUtil.printProductInputMenu();
            String option = scanner.nextLine();
            filler = fillersContainer.get(option);
            if (filler == null)
                System.out.println("Unknown product input mode");
            else {
                repeat = false;
            }
        }
    }

    private void createHTTPServerCommandContainer() {
        HTTPServerCommandContainer = new HashMap<>();
        HTTPServerCommandContainer.put(ServerConstants.HTTP_COMMAND_GET_COUNT, new ReturnNumberOfProductsHTTPCommand(assortmentService));
        HTTPServerCommandContainer.put(ServerConstants.HTTP_COMMAND_GET_ITEM, new ReturnProductInfoHTTPCommand(assortmentService));
    }

    private void createTCPServerCommandContainer() {
        TCPServerCommandContainer = new HashMap<>();
        TCPServerCommandContainer.put("get count", new ReturnNumberOfProductsCommand(assortmentService));
        TCPServerCommandContainer.put("get item", new ReturnProductInfoCommand(assortmentService));
    }

    private void createFillersContainer() {
        fillersContainer = new HashMap<>();
        fillersContainer.put("1", new ConsoleFiller(scanner));
        fillersContainer.put("2", new RandomFiller());
    }

    private void createContainerCommands() {
        commandsContainer = new HashMap<>();
        commandsContainer.put("-1", new ExitCommand(assortmentService, tcpServer, httpServer));
        commandsContainer.put("0", new PrintAllProductsCommand(assortmentService));
        commandsContainer.put("1", new AddToCartCommand(assortmentService, cartService, cartHistoryService, scanner));
        commandsContainer.put("2", new PrintCartCommand(cartService));
        commandsContainer.put("3", new MakeOrderCommand(cartService, orderService, scanner));
        commandsContainer.put("4", new PrintLastNItemsCommand(cartHistoryService));
        commandsContainer.put("5", new PrintOrdersForPeriodCommand(orderService, scanner));
        commandsContainer.put("6", new PrintOrderByDateCommand(orderService, scanner));
        commandsContainer.put("7", new AddProductToAssortmentCommand(scanner, assortmentService, filler));
        commandsContainer.put("8", new AddProductToAssortmentReflectionCommand(scanner, assortmentService, filler));
        commandsContainer.put("9", new ChangeLanguageCommand(scanner));
    }

    public Map<String, Command> getCommandsContainer() {
        return commandsContainer;
    }
}
