package com.shop;

import com.shop.dao.ConsoleInput;
import com.shop.dao.RandomInput;
import com.shop.dao.ShopDAO;
import com.shop.dao.entity.CannedFood;
import com.shop.dao.entity.Food;
import com.shop.dao.entity.Furniture;
import com.shop.dao.entity.Product;
import com.shop.util.Localization;
import com.shop.util.Serializer;
import com.shop.util.ShopProperties;
import com.shop.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.*;

public class Runner {
    private static ShopDAO dao;
    private static Cart cart;
    private static OrderManager manager;
    private static final Logger LOG = LogManager.getLogger(Runner.class);
    private static Scanner scanner = new Scanner(System.in);

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setScanner(Scanner scanner) {
        Runner.scanner = scanner;
    }

    private static boolean init() {
        boolean propertiesLoad = ShopProperties.loadProperties();
        if (propertiesLoad) {
            Localization.loadLocalization(null);
            initDAO();
            cart = new Cart();
            manager = new OrderManager();
        }
        return propertiesLoad;
    }

    public static void main(String[] args) {
        LOG.trace("Start application");
        boolean continueWork = init();
        LOG.trace("Load properties: " + continueWork);
        while (continueWork) {
            printMenu();
            String option = scanner.nextLine();
            LOG.trace("option: " + option);
            switch (option) {
                case "-1": {
                    continueWork = false;
                    LOG.debug("-1 - leave");
                    Serializer.serializeProducts(dao.getProductList());
                    break;
                }
                case "1": {
                    LOG.debug("1 - select all products");
                    printProductList();
                    buyProduct();
                    break;
                }
                case "2": {
                    LOG.debug("2 - show cart content");
                    printCart();
                    break;
                }
                case "3": {
                    LOG.debug("3 - make order");
                    makeOrder();
                    break;
                }
                case "4": {
                    LOG.debug("4 - show last 5 items in the cart");
                    printLast5ItemsInCart();
                    break;
                }
                case "5": {
                    LOG.debug("5 - show all orders for period");
                    printOrdersForPeriod();
                    break;
                }
                case "6": {
                    LOG.debug("6 - show order by date");
                    printOrderByDate();
                    break;
                }
                case "7": {
                    LOG.debug("7 - add a new product to assortment");
                    addNewProduct();
                    break;
                }
                case "8":{
                    LOG.debug("8 - change language");
                    changeLang();
                    break;
                }
                default: {
                    LOG.debug("Default case");
                    System.out.println("Unknown operation.");
                }
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        LOG.trace("print menu");
        System.out.println("=====================================================\n" +
                "1 - select all products\n" +
                "2 - show cart content\n" +
                "3 - make order\n" +
                "4 - show last 5 items in the cart\n" +
                "5 - show all orders for period\n" +
                "6 - show order by date\n" +
                "7 - add a new product to assortment\n" +
                "8 - change language\n" +
                "-1 - leave\n" +
                "=====================================================\n");
    }

    private static void printProductList() {
        LOG.trace("printProductList start");
        List<Product> products = dao.getProductList();
        System.out.println("Available products: \n");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product ID: " + i + "\n" + products.get(i));
        }
        LOG.trace("printProductList end");
    }

    private static void buyProduct() {
        LOG.trace("buyProduct start");
        System.out.println("What product would you like to buy? " +
                "Select id: ");
        try {
            int productID = Integer.parseInt(scanner.nextLine());
            LOG.debug("productID: " + productID);
            Product product = dao.getProduct(productID);
            if (product == null) {
                System.out.println("This product is not available.");
            } else {
                System.out.println("Select quantity: ");
                int quantity = Integer.parseInt(scanner.nextLine());
                LOG.debug("quantity: " + quantity);
                cart.addToCart(product, quantity);
                System.out.println("Product '" + product.getName() + "' was added to the cart.");
            }
        } catch (NumberFormatException exception) {
            LOG.debug("Cannot read int value");
            System.out.println("This isn't number");
        }
        LOG.trace("buyProduct end");
    }

    private static void printCart() {
        LOG.debug("printCart start");
        HashMap<Product, Integer> cartHashMap = cart.getCartHashMap();
        LOG.debug("Cart has products: " + cartHashMap.size());
        if (!cartHashMap.isEmpty()) {
            System.out.println("Your cart: ");
            for (Map.Entry<Product, Integer> entry : cartHashMap.entrySet()) {
                System.out.println(entry.getKey() + "quantity: " + entry.getValue() + "\n");
            }
            System.out.println("Total items: " + cartHashMap.size() +
                    "\nTotal price: " + cart.getTotalPrice());
        } else System.out.println("Your cart is empty");
        LOG.debug("printCart end");
    }

    private static void makeOrder() {
        LOG.trace("makeOrder start");
        if (!cart.getCartHashMap().isEmpty()) {
            System.out.println("Enter order date(" + ShopProperties.getProperty("datetime.format") + "):");
            String textDate = scanner.nextLine();
            LOG.trace("textDate: " + textDate);
            Calendar orderDate = Util.stringToCalendar(textDate, new SimpleDateFormat(ShopProperties.getProperty("datetime.format")));
            if (orderDate != null) {
                System.out.println("Total price: " + manager.makeOrder(cart, orderDate) + " " + ShopProperties.getProperty("product.currency"));
            } else System.out.println("Invalid date format");
        } else System.out.println("Your cart is empty");

        LOG.trace("makeOrder end");
    }

    private static void printLast5ItemsInCart() {
        LOG.trace("printLast5ItemsInCart start");
        System.out.println("Last five items: ");
        List<Product> products = new ArrayList<>(cart.getCartHistory().keySet());
        Collections.reverse(products);
        int counter = 0;
        Iterator<Product> iterator = products.iterator();
        for (int i = 0; i < 5 && iterator.hasNext(); i++) {
            System.out.println(iterator.next());
        }
        LOG.trace("printLast5ItemsInCart end");
    }

    private static void printOrders(TreeMap<Calendar, HashMap<Product, Integer>> orders) {
        System.out.println("Your orders: ");
        for (Map.Entry<Calendar, HashMap<Product, Integer>> orderEntry : orders.entrySet()) {
            String orderDate = Util.calendarToStringDatetime(orderEntry.getKey());
            System.out.println("Order date: " + orderDate +
                    "\nProducts list: \n");
            for (Map.Entry<Product, Integer> productEntry : orderEntry.getValue().entrySet()) {
                System.out.println(productEntry.getKey() + "quantity: " + productEntry.getValue() + "\n");
            }
        }
    }

    private static void printOrdersForPeriod() {
        LOG.trace("printOrdersForPeriod start");
        if (manager.numberOfOrders() < 1) {
            System.out.println("You have no orders");
            LOG.trace("printOrdersForPeriod end");
            return;
        }
        System.out.println("Enter start date(" + ShopProperties.getProperty("date.format") + "): ");
        String startDateString = scanner.nextLine();
        LOG.debug("Start date: " + startDateString);
        SimpleDateFormat calendarFormat = new SimpleDateFormat(ShopProperties.getProperty("date.format"));
        Calendar startDate = Util.stringToCalendar(startDateString, calendarFormat);
        System.out.println("Enter end date(" + ShopProperties.getProperty("date.format") + "): ");
        String endDateString = scanner.nextLine();
        LOG.debug("End date: " + endDateString);
        Calendar endDate = Util.stringToCalendar(endDateString, calendarFormat);
        if (startDate != null && endDate != null) {
            TreeMap<Calendar, HashMap<Product, Integer>> orders = manager.getOrders(startDate, endDate);
            if (!orders.isEmpty()) {
                printOrders(orders);
            } else {
                System.out.println("You have no orders for this period");
                LOG.trace("have no orders for period: " + startDateString + " - " + endDateString);
            }
        } else {
            System.out.println("Invalid date format");
        }
        LOG.trace("printOrdersForPeriod end");
    }

    private static void printOrderByDate() {
        LOG.trace("printOrderByDate start");
        if (manager.numberOfOrders() < 1) {
            System.out.println("You have no orders");
            LOG.trace("printOrderByDate end");
            return;
        }
        System.out.println("Enter date(" + ShopProperties.getProperty("datetime.format") + "): ");
        String dateString = scanner.nextLine();
        LOG.debug("dateString: " + dateString);
        Calendar date = Util.stringToCalendar(dateString, new SimpleDateFormat(ShopProperties.getProperty("datetime.format")));
        if (date != null) {
            Map.Entry<Calendar, HashMap<Product, Integer>> nearestOrder = manager.getOrder(date);
            String orderDate = Util.calendarToStringDatetime(nearestOrder.getKey());
            System.out.println("Order date: " + orderDate +
                    "\nProducts list: \n");
            for (Map.Entry<Product, Integer> productEntry : nearestOrder.getValue().entrySet()) {
                System.out.println(productEntry.getKey() + "quantity: " + productEntry.getValue() + "\n");
            }
        }
        LOG.trace("printOrderByDate end");
    }

    private static void initDAO() {
        System.out.println("Select product input mode:\n1 - Random\n2 - Console");
        String choice = scanner.nextLine();
        LOG.trace("choice: " + choice);
        switch (choice) {
            case "1": {
                LOG.trace("Random input");
                dao = new RandomInput();
                break;
            }
            case "2": {
                LOG.trace("Console input");
                dao = new ConsoleInput();
                break;
            }
            default: {
                LOG.trace("Default input");
                dao = new ConsoleInput();
                break;

            }
        }
    }

    private static void addNewProduct() {
        System.out.println("1 - Add product\n" +
                "2 - Add food\n" +
                "3 - Add canned food\n" +
                "4 - Add furniture\n");
        String choice = scanner.nextLine();
        LOG.trace("choice: " + choice);
        Product product = null;
        switch (choice) {
            case "1": {
                product = new Product();
                break;
            }
            case "2": {
                product = new Food();
                break;
            }
            case "3": {
                product = new CannedFood();
                break;
            }
            case "4": {
                product = new Furniture();
                break;
            }
            default: {
                System.out.println("Shop does not sell such products");
            }
        }
        if (product != null && dao.addNewProductReflection(product)) {
            System.out.println("Product was added");
        } else System.out.println("Cannot add product");
    }

    private static void changeLang()
    {
        System.out.println("1 - English\n" +
                "2 - Russian");
        String choice = scanner.nextLine();
        LOG.trace("choice: " + choice);
        String lang;
        switch (choice) {
            case "1": {
                lang = "en_US";
                break;
            }
            case "2": {
                lang = "ru_UA";
                break;
            }
            default:{
                lang = "en_US";
            }
        }
        Localization.loadLocalization(new Locale(lang));
        LOG.trace("lang: "+lang);
        System.out.println("Current language: "+lang);
    }
}
