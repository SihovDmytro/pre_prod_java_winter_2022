package com.shop;

import com.shop.dao.entity.Furniture;
import com.shop.dao.entity.Product;
import com.shop.util.ShopProperties;
import com.shop.util.Util;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

class OrderManagerTest {
    OrderManager manager;

    @Test
    public void shouldAddNewOrder() {
        manager = new OrderManager();
        Cart cart = new Cart();
        Product product1 = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        Product product2 = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cart.addToCart(product1, 1);
        cart.addToCart(product2, 2);
        Calendar orderDate = Calendar.getInstance();
        manager.makeOrder(cart, orderDate);

        int expected = 1;
        int actual = manager.numberOfOrders();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnNearestOrderByDate() {
        manager = new OrderManager();
        ShopProperties.loadProperties();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Cart cart = new Cart();
        Product firstProduct = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        cart.addToCart(firstProduct, 1);
        Calendar firstOrderDate = Util.stringToCalendar("28.02.2022 09:51:56", format);
        manager.makeOrder(cart, firstOrderDate);
        Product secondProduct = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cart.addToCart(secondProduct, 2);
        Calendar secondOrderDate = Util.stringToCalendar("01.12.2022 09:51:56", format);
        manager.makeOrder(cart, secondOrderDate);

        HashMap<Product, Integer> expectedProducts = new HashMap<>();
        expectedProducts.put(secondProduct, 2);
        Map.Entry<Calendar, HashMap<Product, Integer>> expected = new AbstractMap.SimpleEntry<>(secondOrderDate, expectedProducts);
        Calendar enteredDate = Util.stringToCalendar("19.08.2022 09:51:56", format);
        Map.Entry<Calendar, HashMap<Product, Integer>> actual = manager.getOrder(enteredDate);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnOrdersForSpecifiedPeriod() {
        manager = new OrderManager();
        ShopProperties.loadProperties();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Cart cart = new Cart();
        Product firstProduct = new Furniture(new BigDecimal(2499), "dinner table", 56, 125, 140);
        cart.addToCart(firstProduct, 1);
        Calendar firstOrderDate = Util.stringToCalendar("28.02.2022 09:51:56", format);
        manager.makeOrder(cart, firstOrderDate);
        Product secondProduct = new Furniture(new BigDecimal(5500), "cupboard", 200, 65, 198);
        cart.addToCart(secondProduct, 2);
        Calendar secondOrderDate = Util.stringToCalendar("01.12.2022 09:51:56", format);
        manager.makeOrder(cart, secondOrderDate);

        Calendar startDate = Util.stringToCalendar("19.12.2021 09:51:56", format);
        Calendar endDate = Util.stringToCalendar("02.12.2022 09:51:56", format);

        Assertions.assertEquals(2, manager.getOrders(startDate, endDate).size());
    }
}