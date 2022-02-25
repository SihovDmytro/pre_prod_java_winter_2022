package com.task4;

import com.task1.subtask1.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Order;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Controller {
    private static ShopDAO dao = new ShopDAO();
    private static Cart cart = new Cart();
    private static OrderManager manager = new OrderManager();
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        LOG.trace("Start application");
        boolean continueWork = ShopProperties.loadProperties();
        while (continueWork) {
            LOG.debug("Print menu");
            printMenu();
            switch (scanner.next()) {
                case "-1": {
                    continueWork = false;
                    LOG.debug("Inside '-1' case");
                    break;
                }
                case "1": {
                    LOG.debug("Inside '1' case");
                    printProductList();
                    buyProduct();
                    break;
                }
                case "2": {
                    LOG.debug("Inside '2' case");
                    printCart();
                    break;
                }
                case "3": {
                    LOG.debug("Inside '3' case");
                    makeOrder();
                    break;
                }
                case "4": {
                    LOG.debug("Inside '4' case");
                    printLast5ItemsInCart();
                    break;
                }
                default: {
                    LOG.debug("Default case");
                    System.out.println("Unknown operation.");
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("=====================================================\n" +
                "1 - select all products\n" +
                "2 - show cart content\n" +
                "3 - make order\n" +
                "4 - show last 5 items in the cart\n" +
                "5 - show all orders for period\n" +
                "-1 - leave\n" +
                "=====================================================\n");
    }

    private static void printProductList() {
        List<Product> products = dao.getProductList();
        System.out.println("Available products: \n");
        for (int i = 0; i < products.size(); i++) {
            System.out.println("Product ID: " + i + "\n" + products.get(i));
        }
    }

    private static void buyProduct() {
        System.out.println("What product would you like to buy? " +
                "Select id: ");
        try {
            int productID = scanner.nextInt();
            Product product = dao.getProduct(productID);
            if (product == null) {
                System.out.println("This product is not available.");
            } else {
                System.out.println("Select quantity: ");
                cart.addToCart(product, scanner.nextInt());
                System.out.println("Product '" + product.getName() + "' was added to the cart.");
            }
        } catch (InputMismatchException exception) {
            LOG.debug("Cannot read int value");
            System.out.println("This isn't number");
        }
    }

    private static void printCart() {
        HashMap<Product, Integer> cartHashMap = cart.getCartHashMap();
        LOG.debug("Cart has products: " + cartHashMap.size());
        if (cartHashMap.isEmpty()) {
            System.out.println("Your cart is empty");
            return;
        }
        System.out.println("Your cart: ");
        for (Map.Entry<Product, Integer> entry : cartHashMap.entrySet()) {
            System.out.println(entry.getKey() + "quantity: " + entry.getValue() + "\n");
        }
        System.out.println("Total items: " + cartHashMap.size() +
                "\nTotal price: " + cart.getTotalPrice());
    }

    private static void makeOrder() {
        if (cart.getCartHashMap().isEmpty()) {
            System.out.println("Your cart is empty");
            return;
        }
        String textDate = null;
        try {
            Calendar orderDate = Calendar.getInstance();
            System.out.println("Enter order date(dd.MM.yyyy HH:mm:ss):");
            textDate = scanner.next();
            LOG.trace("Input date: "+textDate);
            SimpleDateFormat sdf = new SimpleDateFormat(ShopProperties.getProperty("date.format"));
            Date date = sdf.parse(textDate);
            orderDate.setTime(date);
            System.out.println("Total price: " + manager.makeOrder(cart,orderDate));
        } catch (ParseException exception) {
            LOG.debug("Cannot parse order date");
            System.out.println("Invalid date format");
        }
    }

    private static void printLast5ItemsInCart() {
        System.out.println("Last five items: ");
        List<Product> products = new ArrayList<>(cart.getCartHistory().keySet());
        for (int i = products.size() - 1; i >= products.size() - 5; i--) {
            System.out.println(products.get(i));
        }
    }
}
